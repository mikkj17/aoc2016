import java.io.File
import kotlin.math.max
import kotlin.math.min

private fun getNewPos(currentPosition: Pair<Int, Int>, move: Char, max: Int): Pair<Int, Int> {
    val pair = when (move) {
        'U' -> {
            Pair(currentPosition.first - 1, currentPosition.second)
        }
        'D' -> {
            Pair(currentPosition.first + 1, currentPosition.second)
        }
        'L' -> {
            Pair(currentPosition.first, currentPosition.second - 1)
        }
        else -> {
            Pair(currentPosition.first, currentPosition.second + 1)
        }
    }
    return Pair(max(0, min(max, pair.first)), max(0, min(max, pair.second)))
}

private fun posToNum(position: Pair<Int, Int>): Int {
    val nums = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
    )
    return nums[position.first][position.second]
}

private fun partOne(inp: String): Int {
    var position = Pair(1, 1)
    var code = ""
    for (line in inp.split('\n')) {
        for (instruction in line) {
            position = getNewPos(position, instruction, 2)
        }
        code += posToNum(position).toString()
    }
    return code.toInt()
}

private fun posToCode(currentPosition: Pair<Int, Int>): Char {
    val codes = listOf(
        listOf('0', '0', '1', '0', '0'),
        listOf('0', '2', '3', '4', '0'),
        listOf('5', '6', '7', '8', '9'),
        listOf('0', 'A', 'B', 'C', '0'),
        listOf('0', '0', 'D', '0', '0'),
    )
    return codes[currentPosition.first][currentPosition.second]
}

private fun partTwo(inp: String): String {
    var position = Pair(2, 0)
    var code = ""
    for (line in inp.split('\n')) {
        for (instruction in line) {
            val newPosition = getNewPos(position, instruction, 4)
            if (posToCode(newPosition) != '0') {
                position = newPosition
            }
        }
        code += posToCode(position)
    }
    return code
}

fun main() {
    val inp = File("src/main/resources/day02.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
