package day_6

import java.io.File
import java.nio.file.Paths

val DAY6_FILE_NAME = "day_6\\input.txt"

fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\${DAY6_FILE_NAME}"
    readFile(path)
}

fun readFile(filename: String) {
    val lines = File(filename).readLines()
    val regex = "\\s+".toRegex()
    val times = lines[0].split(": ")[1].split(regex).drop(1)
    val distances = lines[1].split(": ")[1].split(regex).drop(1)
    partFirstSolution(times, distances)
    partTwoSolution(times, distances)
}

fun partFirstSolution(times: List<String>, distances: List<String>) {
    val timesDistances = (times zip distances).map { timesDistance ->
        Pair(timesDistance.first.toInt(), timesDistance.second.toInt())
    }
    var totalCounter = 1
    timesDistances.forEach { (totalTime, totalDistance) ->
        var counter = 0
        for (speed in 0..totalTime) {
            if (speed.times(totalTime - speed) > totalDistance) {
                counter++
            }
        }
        totalCounter = totalCounter.times(counter)
    }
    println(totalCounter)
}

fun partTwoSolution(times: List<String>, distances: List<String>) {
    val time= times.joinToString("").toLong()
    val distance = distances.joinToString("").toLong()
    var counter = 0
    for (speed in 0..time) {
        if (speed.times(time - speed) > distance) {
            counter++
        }
    }
    println(counter)
}