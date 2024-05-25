package day_5

import java.io.File
import java.lang.Long.MAX_VALUE
import java.nio.file.Paths
import kotlin.system.measureTimeMillis

const val SOIL = "soil"
const val SEED = "seed"
const val FERTILIZER = "fertilizer"
const val LIGHT = "light"
const val WATER = "water"
const val TEMPERATURE = "temperature"
const val HUMIDITY = "humidity"


val DAY5_FILE_NAME = "day_5\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY5_FILE_NAME"
    readFile(path)
}

lateinit var seedToSoilMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var soilToFertilizerMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var fertilizerToWaterMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var waterToLightMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var lightToTemperatureMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var temperatureToHumidityMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
lateinit var humidityToLocationMap: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>
fun readFile(filename: String) {
    val lines = File(filename).bufferedReader().readLines()
    val seeds = lines[0].split(": ")[1].split(" ").map { it.toLong() }
    val maps = mutableMapOf<String, MutableList<Pair<Pair<Long, Long>, Pair<Long, Long>>>>()
    var currentCategory = ""
    lines.drop(2).forEach {line ->
        when {
            line.isBlank() -> currentCategory = ""
            currentCategory.isEmpty() -> currentCategory = line.split(" ")[0].split("-")[0]
            else -> {
                val values = line.split(" ").map { it.toLong() }
                maps.computeIfAbsent(currentCategory) { mutableListOf() }
                    .add(Pair(Pair(values[1], values[1] + values[2]), Pair(values[0], values[0] + values[2])))
            }
        }
    }
    seedToSoilMap = maps[SEED] ?: mutableListOf()
    soilToFertilizerMap = maps[SOIL] ?: mutableListOf()
    fertilizerToWaterMap = maps[FERTILIZER] ?: mutableListOf()
    waterToLightMap = maps[WATER] ?: mutableListOf()
    lightToTemperatureMap = maps[LIGHT] ?: mutableListOf()
    temperatureToHumidityMap = maps[TEMPERATURE] ?: mutableListOf()
    humidityToLocationMap = maps[HUMIDITY] ?: mutableListOf()

    var min = MAX_VALUE
    for(i in seeds.indices step 2) {
        val startSeed = seeds[i]
        val endSeed = startSeed + seeds[i + 1]
        val duration = measureTimeMillis {
            for (j in startSeed..endSeed) {
                val new = mapSeedsToMinLocation(j)
                if (new < min) {
                    min = new
                }
            }
        }
        println("Mapping for seed range $startSeed-$endSeed took $duration")
    }
    println(min)
}

fun mapSeedsToMinLocation(seed: Long): Long {
    fun mapValue(value: Long, map: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>): Long {
        return map.firstOrNull { value in it.first.first..<it.first.second }
            ?.let { it.second.first + value - it.first.first }
            ?: value
    }

    var currentValue = seed
    currentValue = mapValue(currentValue, seedToSoilMap)
    currentValue = mapValue(currentValue, soilToFertilizerMap)
    currentValue = mapValue(currentValue, fertilizerToWaterMap)
    currentValue = mapValue(currentValue, waterToLightMap)
    currentValue = mapValue(currentValue, lightToTemperatureMap)
    currentValue = mapValue(currentValue, temperatureToHumidityMap)
    currentValue = mapValue(currentValue, humidityToLocationMap)

    return currentValue
}