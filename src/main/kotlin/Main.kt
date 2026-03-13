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

// State of a cargo exiting a station (station id and cargo)
data class State(val id: Int, val cargo: Int)

fun calcAvailableCargo(railway: Railway) : Map<Int, Set<Int>> {
    val queue: ArrayDeque<State> = ArrayDeque()
    val visited: MutableSet<State> = mutableSetOf()
    val cargo: MutableMap<Int, Set<Int>> = railway.stations.mapValues { emptySet<Int>() }.toMutableMap()

    val startStation = railway.stations.getValue(railway.startStationId)
    val firstState = State(railway.startStationId, startStation.load)
    queue.add(firstState)
    visited.add(firstState)

    while (queue.isNotEmpty()) {
        val (stationId, currentCargo) = queue.removeFirst()
        for (neighbor in railway.tracks.getOrDefault(stationId, listOf())) {
            cargo[neighbor] = cargo.getOrDefault(neighbor, emptySet()) + currentCargo
            val neighborStation: Station = railway.stations.getValue(neighbor)
            if (neighborStation.unload != currentCargo) {
                val nextState = State(neighbor, currentCargo)
                if (!visited.contains(nextState)) {
                    queue.add(nextState)
                    visited.add(nextState)
                }
            }
            val newState = State(neighbor, neighborStation.load)
            if (!visited.contains(newState)) {
                queue.add(newState)
                visited.add(newState)
            }
        }
    }
    return cargo
}

fun formatOutput(output: Map<Int, Set<Int>>): String {
    return output.entries.joinToString("\n") { (stationId, cargoState) ->
        "$stationId ${cargoState.sorted().joinToString(" ")}".trim()
    }
}