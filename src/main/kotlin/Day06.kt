import java.io.File

private fun getFrequencies(str: String): Map<Char, Int> {
    val frequencies = mutableMapOf<Char, Int>()
    for (char in str) {
        frequencies[char] = frequencies.getOrDefault(char, 0) + 1
    }
    return frequencies
}

private fun partOne(inp: String): String {
    val messages = inp.split('\n')
    var mostCommons = ""
    for (j in 0 until messages.first().length) {
        val column = messages.map { it[j] }.joinToString("")
        mostCommons += getFrequencies(column).toList().maxBy { it.second }.first
    }
    return mostCommons
}

private fun partTwo(inp: String): String {
    val messages = inp.split('\n')
    var leastCommons = ""
    for (j in 0 until messages.first().length) {
        val column = messages.map { it[j] }.joinToString("")
        leastCommons += getFrequencies(column).toList().minBy { it.second }.first
    }
    return leastCommons
}

fun main() {
    val inp = File("src/main/resources/day06.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
