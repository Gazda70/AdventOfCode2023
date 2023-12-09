package day_2
import java.io.File
import java.nio.file.Paths

val RED_CUBES=12
val GREEN_CUBES=13
val BLUE_CUBES=14

val DAY2_FILE_NAME = "day_2\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY2_FILE_NAME"
    read_file(path)
}
fun read_file(filename:String) {
    var sum = 0
    var sumOfPowers = 0
    File(filename).forEachLine {
        val (gameIDPart, trialsPart) = it.split(": ")
        println(getGameID(gameIDPart))
        val game = parseGameStringToMap(trialsPart)
        if(checkIfGameIsPossible(game)) {
            sum += getGameID(gameIDPart)
        }
        sumOfPowers += getMinimalSetForGame(game)
    }
    println(sum)
    println(sumOfPowers)
}

fun getGameID(game:String): Int {
    val t = game.split(" ")
    return t[1].toInt()
}
fun parseGameStringToMap(game:String) = game.split("; ").map{mapTrial(it)}
fun checkIfGameIsPossible(game:List<Map<String, Int>>):Boolean {
    var iter = 0
    while(iter < game.size) {
        if(!assessTrial(game[iter])) {
            return false
        }
        iter++
    }
    return true
}
fun getMinimalSetForGame(game:List<Map<String, Int>>): Int {
    val maxMap = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)
    game.forEach {
        if(it.containsKey("red") && it["red"]!! > maxMap["red"]!!) {
            maxMap["red"] = it.get("red")!!
        }
        if(it.containsKey("green") && it["green"]!! > maxMap["green"]!!) {
            maxMap["green"] = it.get("green")!!
        }
        if(it.containsKey("blue") && it["blue"]!! > maxMap["blue"]!!) {
            maxMap["blue"] = it.get("blue")!!
        }
    }
    return maxMap["red"]!! * maxMap["green"]!! * maxMap["blue"]!!
}
fun mapTrial(trial:String) = trial.split(", ")
        .map{it.split(" ")}
        .map{it.last() to it.first().toInt()}.toMap()
fun assessTrial(trial:Map<String, Int>) = (!trial.containsKey("red") || trial["red"]!! <= RED_CUBES) &&
        (!trial.containsKey("blue") ||trial["blue"]!! <= BLUE_CUBES) &&
        (!trial.containsKey("green") ||trial["green"]!! <= GREEN_CUBES)
