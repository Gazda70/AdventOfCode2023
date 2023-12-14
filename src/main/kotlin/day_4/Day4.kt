package day_4

import java.io.File
import java.nio.file.Paths
import kotlin.math.pow

val DAY2_FILE_NAME = "day_4\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY2_FILE_NAME"
    readFile(path)
}


fun readFile(filename: String) {
    val lines = File(filename).bufferedReader().readLines()
    val cards = lines.map { it ->
        val (winning, players) = it.split(": ")[1].split(" | ")
        winning.split(" ")
            .filter { it.isNotEmpty() }.intersect(
                players.split(" ")
                    .filter { it.isNotEmpty() }.toSet()
            ).count()
    }.toList()
    val result = cards.sumOf {
        if (it != 0) {
            2.0.pow(it - 1)
        } else {
            0.0
        }
    }
    println(result)

    val wonCards = mutableMapOf<Int, Int>().apply {
        for (i in cards.indices) this[i] = 1
    }

    cards.indices.forEach { i ->
        (1..wonCards[i]!!).forEach {
            (1..cards[i]).forEach { j ->
                wonCards[i + j] = wonCards[i + j]?.plus(1) ?: 0
            }
        }
    }
    println(wonCards.values.sum())
}