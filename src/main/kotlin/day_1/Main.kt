package day_1

import java.io.File
import java.nio.file.Paths

val FILE_NAME = "day_1\\text_input.txt"
fun main(args: Array<String>) {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$FILE_NAME"
    sumNumbersFromFileLines(path)
}

fun sumNumbersFromFileLines(fileName: String) {
    var sum = 0
    var lineCount = 0
    File(fileName).forEachLine {
        sum += twoBorderDigitsNumberFromString(it)
        lineCount++
    }
    println(lineCount)
    println(sum)
}

fun twoBorderDigitsNumberFromString(someString: String): Int {
    var firstDigit: Int? = null
    var lastDigit: Int? = null
    for(i in 0..someString.length - 1) {
        if(someString[i].isDigit()) {
            if(firstDigit == null) {
                firstDigit = someString[i].digitToInt()
            } else {
                lastDigit = someString[i].digitToInt()
            }
        }
    }
    if(firstDigit == null) {
        firstDigit = 0
    }
    if(lastDigit == null) {
        lastDigit = firstDigit
    }
    return 10*firstDigit + lastDigit
}