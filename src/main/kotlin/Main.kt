package org.example

import java.util.Scanner

data class Station(val unload: Int, val load: Int)
data class Railway(val stations: Map<Int, Station>, val tracks: Map<Int, List<Int>>, val startStationId: Int)

fun main() {
    val scanner = Scanner(System.`in`)
    val railway = parseInput(scanner)
    val result = calcAvailableCargo(railway)
    println(formatOutput(result))
}

fun parseInput(scanner: Scanner): Railway {
    val numStations = scanner.nextInt()
    val numTracks = scanner.nextInt()
    val stations: MutableMap<Int, Station> = mutableMapOf()
    for (i in 0 until numStations) {
        val stationId = scanner.nextInt()
        val unload = scanner.nextInt()
        val load = scanner.nextInt()
        stations[stationId] = Station(unload, load)
    }
    val tracks: MutableMap<Int, MutableList<Int>> = HashMap()
    for (i in 0 until numTracks) {
        val trackStartId = scanner.nextInt()
        val trackEndId = scanner.nextInt()
        tracks.getOrPut(trackStartId) { mutableListOf() }.add(trackEndId)
    }
    val startStationId = scanner.nextInt()
    return Railway(stations, tracks, startStationId)
}

// State of a rail entering a station (station id and that may enter via this rail)
data class EntryState(val id: Int, val cargoState: Set<Int>)

fun calcAvailableCargo(railway: Railway) : Map<Int, Set<Int>> {
    val queue: ArrayDeque<EntryState> = ArrayDeque()
    val cargo: MutableMap<Int, Set<Int>> = railway.stations.mapValues { emptySet<Int>() }.toMutableMap()

    queue.add(EntryState(railway.startStationId, emptySet()))

    while (queue.isNotEmpty()) {
        val (stationId, cargoState) = queue.removeFirst()
        val station: Station = railway.stations.getValue(stationId)
        val newCargo = (cargoState - station.unload) + station.load
        for (neighbor in railway.tracks.getOrDefault(stationId, listOf())) {
            if (cargo.getValue(neighbor).containsAll(newCargo)) {
                continue
            }
            cargo[neighbor] = cargo.getOrDefault(neighbor, emptySet()) + newCargo
            queue.add(EntryState(neighbor, newCargo))
        }
    }
    return cargo
}

fun formatOutput(output: Map<Int, Set<Int>>): String {
    return output.entries.joinToString("\n") { (stationId, cargoState) ->
        "$stationId ${cargoState.sorted().joinToString(" ")}".trim()
    }
}