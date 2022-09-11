import java.io.File
import kotlin.math.abs

private fun getDirectionsMap(): Map<Char, (Int) -> Int> {
    return mapOf(
        'R' to {direction: Int -> (direction + 1) % 4},
        'L' to {direction: Int -> if (direction == 0) 3 else (direction - 1) % 4},
    )
}

private fun getNewPos(xPos: Int, yPos: Int, facingDirection: Int, nBlocks: Int): Pair<Int, Int> {
    var newXPos = xPos
    var newYPos = yPos
    when (facingDirection) {
        0 -> {
            newYPos += nBlocks
        }
        1 -> {
            newXPos += nBlocks
        }
        2 -> {
            newYPos -= nBlocks
        }
        3 -> {
            newXPos -= nBlocks
        }
    }
    return Pair(newXPos, newYPos)
}

private fun partOne(inp: String): Int {
    val directions = getDirectionsMap()
    var xPos = 0
    var yPos = 0
    var facingDirection = 0
    for (instruction in inp.split(", ")) {
        val direction = instruction.first()
        facingDirection = directions[direction]?.invoke(facingDirection)!!
        val nBlocks = instruction.drop(1).toInt()
        val newPosition = getNewPos(xPos, yPos, facingDirection, nBlocks)
        xPos = newPosition.first
        yPos = newPosition.second
    }
    return abs(xPos) + abs(yPos)
}

private fun newVisits(
    xPos: Int,
    yPos: Int,
    newXPos: Int,
    newYPos: Int,
    visited: MutableSet<Pair<Int, Int>>,
    facingDirection: Int
): Int {
    if (facingDirection in listOf(1, 3)) { // moving east or west
        val range =
            if (xPos < newXPos) xPos + 1..newXPos
            else xPos - 1 downTo newXPos
        for (i in range) {
            val position = Pair(i, yPos)
            if (position in visited) {
                return abs(position.first) + abs(position.second)
            }
            visited.add(position)
        }
    }
    else {
        val range =
            if (yPos < newYPos) yPos + 1..newYPos
            else yPos - 1 downTo newYPos
        for (i in range) {
            val position = Pair(xPos, i)
            if (position in visited) {
                return abs(position.first) + abs(position.second)
            }
            visited.add(position)
        }
    }
    return -1
}

private fun partTwo(inp: String): Int {
    val directions = getDirectionsMap()
    val visited = mutableSetOf(Pair(0, 0))
    var xPos = 0
    var yPos = 0
    var facingDirection = 0
    for (instruction in inp.split(", ")) {
        val direction = instruction.first()
        facingDirection = directions[direction]?.invoke(facingDirection)!!
        val nBlocks = instruction.drop(1).toInt()
        val newPosition = getNewPos(xPos, yPos, facingDirection, nBlocks)
        val newXPos = newPosition.first
        val newYPos = newPosition.second
        val newVisits = newVisits(xPos, yPos, newXPos, newYPos, visited, facingDirection)
        if (newVisits != -1) {
            return newVisits
        }
        xPos = newXPos
        yPos = newYPos
    }
    return 0
}

fun main() {
    val inp = File("src/main/resources/day01.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
