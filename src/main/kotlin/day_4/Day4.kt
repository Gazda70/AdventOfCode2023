package day_4

import java.io.File
import java.nio.file.Paths
import kotlin.math.pow

val DAY2_FILE_NAME = "day_4\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY2_FILE_NAME"
    read_file(path)
}


fun read_file(filename:String) {
    val res = File(filename).bufferedReader().readLines().map{ it ->
        val (winning, players) = it.split(": ")[1].split(" | ")
        val intersect = winning.split(" ").filter{it != ""}.intersect(players.split(" ").filter{it != ""}.toSet())
        var result = 0.0
        if(intersect.isNotEmpty()) {
           result = 2.0.pow(intersect.count() - 1)
        }
        result
    }.sum()
    println(res)
}