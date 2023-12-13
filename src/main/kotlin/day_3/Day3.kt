package day_3

import java.io.File
import java.nio.file.Paths

val DAY2_FILE_NAME = "day_3\\input.txt"
fun main() {
    val path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$DAY2_FILE_NAME"
    read_file(path)
}

fun read_file(filename:String) {
    val symbolsList = mutableListOf<Char>()
    val file = File(filename)
    val arrayWidth = file.readLines()[0].length
    val arrayHeight = file.readLines().size
    println("Matrix of size: " + arrayWidth + "x" + arrayHeight)
    val characterArray = Array(arrayWidth) {IntArray(arrayHeight)}
    var rowCount = 0
    file.forEachLine { it ->
        var columnCount = 0
        it.chars().forEach{
            if(it.toChar() != '.' && !it.toChar().isDigit() && !symbolsList.contains(it.toChar())){
                symbolsList.add(it.toChar())
            }
            characterArray[rowCount][columnCount] = it
            columnCount++
        }
        rowCount++
    }
    println(symbolsList)
    val partNumbers = mutableListOf<Int>()
    var sum = 0
    var number = ""
    var isASymbolNeighbouring = false
    var collectingDigit = false
    for(i in 0..characterArray.size - 1) {
        for(j in 0..characterArray[i].size - 1) {
            if(characterArray[i][j].toChar().isDigit()) {
                collectingDigit = true
                number += characterArray[i][j].toChar()
                if(checkIfASymbolIsNeighbouring(characterArray, i, j, symbolsList)) {
                    isASymbolNeighbouring = true
                    printInRed(characterArray[i][j].toChar())
                } else {
                    print(characterArray[i][j].toChar())
                }
            } else {
                print(characterArray[i][j].toChar())
                if (collectingDigit) {
                    if(isASymbolNeighbouring) {
                        partNumbers.add(number.toInt())
                        sum += number.toInt()
                        isASymbolNeighbouring = false
                    }
                    collectingDigit = false
                    number = ""
                }
            }
        }
        println()
    }
    println(partNumbers)
    println(partNumbers.sum())
    println(sum)
}

fun printInRed(text:Char) {
    // Everything after this is in red
    val red = "\u001b[31m"

// Resets previous color codes
    val reset = "\u001b[0m"
    print(red + text + reset)
}
fun checkIfASymbolIsNeighbouring(array:Array<IntArray>, posX:Int, posY:Int, symbolsList: List<Char>): Boolean {
    val maxWidth = array[0].size
    val maxHeight = array.size
    if(posX + 1 < maxWidth) {
        if (symbolsList.contains(array[posX+1][posY].toChar())) {
            return true
        }
        if (posY + 1 < maxHeight) {
            if (symbolsList.contains(array[posX+1][posY+1].toChar())) return true
        }
        if ((posY - 1 >= 0)) {
            if (symbolsList.contains(array[posX+1][posY-1].toChar())) return true
        }
    }
    if(posX - 1 > 0) {
        if (symbolsList.contains(array[posX-1][posY].toChar())) {
            return true
        }
        if (posY + 1 < maxHeight) {
            if (symbolsList.contains(array[posX-1][posY+1].toChar())) return true
        }
        if ((posY - 1 >= 0)) {
            if (symbolsList.contains(array[posX-1][posY-1].toChar())) return true
        }
    }
    if(posY + 1 < maxHeight && symbolsList.contains(array[posX][posY+1].toChar())) {
        return true
    }
    if(posY - 1 >= 0 && symbolsList.contains(array[posX][posY-1].toChar())) {
        return true
    }
    return false
}