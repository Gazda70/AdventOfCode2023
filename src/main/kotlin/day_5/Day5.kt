package day_5

import java.io.File
import java.nio.file.Paths

val SOIL = "soil"
val SEED = "seed"
val FERTILIZER = "fertilizer"
val WATER = "water"
val LIGHT = "light"
val TEMPERATURE = "temperature"
val HUMIDITY = "humidity"
val LOCATION = "location"


val DAY5_FILE_NAME = "day_5\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY5_FILE_NAME"
    readFile(path)
}

var seedToSoilMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var soilToFertilizerMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var fertilizerToWaterMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var waterToLightMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var lightToTemperatureMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var temperatureToHumidityMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
var humidityToLocationMap = mutableListOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()
fun readFile(filename: String) {
    val lines = File(filename).bufferedReader().readLines()
    val seeds = lines[0].split(": ")[1].split(" ").map{it.toLong()}
    var actualMap: MutableList<Pair<Pair<Long, Long>, Pair<Long, Long>>> = mutableListOf()
    var actualCategory = ""
    var collectingMap = false
    for(i in 2..lines.size - 1) {
        if(lines[i-1] == "") {
            actualCategory = lines[i].split(" ")[0].split("-")[0]
            collectingMap = true
        } else if(lines[i] == "") {
            when {
                actualCategory == SEED -> seedToSoilMap = actualMap
                actualCategory == SOIL -> soilToFertilizerMap = actualMap
                actualCategory == FERTILIZER -> fertilizerToWaterMap = actualMap
                actualCategory == WATER -> waterToLightMap = actualMap
                actualCategory == LIGHT -> lightToTemperatureMap = actualMap
                actualCategory == TEMPERATURE -> temperatureToHumidityMap = actualMap
                actualCategory == HUMIDITY -> humidityToLocationMap = actualMap
            }
            actualCategory = ""
            actualMap = mutableListOf()
            collectingMap = false
        } else if(collectingMap) {
            val values = lines[i].split(" ").map{it.toLong()}
            actualMap.add(Pair(Pair(values[1], values[1] + values[2]), Pair(values[0], values[0] + values[2])))
        }
    }
    val min = seeds.map{seed ->
        try {
            val pair = seedToSoilMap.first { element -> seed >= element.first.first && seed < element.first.second }
            pair.second.first + seed - pair.first.first
        } catch (exception:NoSuchElementException ) {
            seed
        }
    }.map{soil ->
        try {
            val pair = soilToFertilizerMap.first { element -> soil >= element.first.first && soil < element.first.second }
            pair.second.first + soil - pair.first.first
        } catch (exception:NoSuchElementException ) {
            soil
        }
    }.map{fertilier ->
        try {
            val pair = fertilizerToWaterMap.first { element -> fertilier  >= element.first.first && fertilier  < element.first.second }
            pair.second.first + fertilier  - pair.first.first
        } catch (exception:NoSuchElementException ) {
            fertilier
        }
    }.map{water ->
        try {
            val pair = waterToLightMap.first { element -> water  >= element.first.first && water  < element.first.second }
            pair.second.first + water  - pair.first.first
        } catch (exception:NoSuchElementException ) {
            water
        }
    }.map{light ->
        try {
            val pair = lightToTemperatureMap.first { element -> light  >= element.first.first && light  < element.first.second }
            pair.second.first + light  - pair.first.first
        } catch (exception:NoSuchElementException ) {
            light
        }
    }.map{temperature ->
        try {
            val pair = temperatureToHumidityMap.first { element -> temperature  >= element.first.first && temperature  < element.first.second }
            pair.second.first + temperature  - pair.first.first
        } catch (exception:NoSuchElementException ) {
            temperature
        }
    }.map{humidity ->
        try {
            val pair = humidityToLocationMap.first { element -> humidity  >= element.first.first && humidity  < element.first.second }
            pair.second.first + humidity  - pair.first.first
        } catch (exception:NoSuchElementException ) {
            humidity
        }
    }.min()
    println(min)
}