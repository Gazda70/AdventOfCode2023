package day_4

import java.io.File
import java.nio.file.Paths
import kotlin.math.pow

val DAY2_FILE_NAME = "day_4\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY2_FILE_NAME"
    readFile(path)
}


fun readFile(filename:String) {
    val result = File(filename).bufferedReader().readLines().sumOf { it ->
        val (winning, players) = it.split(": ")[1].split(" | ")
        val intersect = winning.split(" ")
            .filter { it.isNotEmpty() }.intersect(players.split(" ")
                .filter { it.isNotEmpty() }.toSet()
            )
        if (intersect.isNotEmpty()) {
            2.0.pow(intersect.size - 1)
        } else {
            0.0
        }
    }
    println(result)
}