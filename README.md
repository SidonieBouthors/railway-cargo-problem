# Railway Cargo Problem

## Usage

To run the program, use the following command:
```
./gradlew run
```

Then type the input in the following format:

- First line: `S T` (number of stations and tracks)
- Next S Lines: `s c_unload c_load` (station ID, unload cargo type, load cargo type)
- Next T Lines: `s_from s_to` (directed track from one station to another)
- Last Line: `s_0` (starting station ID)

See `exampleInput.txt` for an example.

For simplicity, you can also write the input to a file and run:
```
cat exampleInput.txt | ./gradlew run
```

The output format is one line per station, starting with the station ID 
and followed by a list of all the cargo types that can arrive at this station.

To run the tests, use:
```
./gradlew test
```


## Algorithm

The idea of the algorithm is to traverse the railway, starting from the start station, 
in a BFS (breadth first search) fashion. We have to keep track of the cargo that can reach
each station, so instead of enqueuing just stations, we enqueue (station, cargo) pairs that 
represent a station and some cargo that leaves that station. Essentially our queue doesn't store 
graph nodes, but the cargo exiting a particular station, and when we dequeue this state we will look
at all the neighbors of that station and consider that they will receive this particular cargo.

We keep record of all cargo that has reached a station as our final result, and we don't enqueue
a state (station & cargo) if this state has already been visited.

The time complexity of my approach can be thought about like this (where C is the number of cargo types, 
S the number of stations and T the number of tracks)
- We dequeue each possible (station, cargo) pair at most once
- For each dequeue, we look at all neighbors of that station
- For each neighbor of a dequeued state, we perform only O(1) operations

So we take the sum over all (station, cargo) pairs of looking at all the outgoing edges from the station, 
we can extract C as a constant factor of that sum and the sum over all stations of outgoing edges turns out to be T.
So overall our time complexity is about O(C.T)

## Example

The below image is a representation of one of the tests, that shows the unload cargo types in red, 
the load cargo types in green and the cargo types that can reach a particular station in pink.

You can find the input for this railway in `exampleInput.txt`.

![example](img.png)