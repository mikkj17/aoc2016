import java.io.File
import java.util.Collections

private fun turnOn(screen: MutableList<MutableList<Boolean>>, a: Int, b: Int) {
    for (x in 0 until a) {
        for (y in 0 until b) {
            screen[y][x] = true
        }
    }
}

private fun rotate(screen: MutableList<MutableList<Boolean>>, axis: String, index: Int, pixels: Int) {
    if (axis == "row") {
        val row = screen[index]
        Collections.rotate(row, pixels)
        screen[index] = row
    }
    else {
        val column = screen.map { it[index] }
        Collections.rotate(column, pixels)
        for (y in 0 until screen.size) {
            screen[y][index] = column[y]
        }
    }
}

private fun applyOperation(screen: MutableList<MutableList<Boolean>>, operation: String) {
    val rectangle = Regex("""^rect (\d+)x(\d+)$""")
    val rotation = Regex("""^rotate (row|column) [xy]=(\d+) by (\d+)$""")

    val rectMatch = rectangle.matchEntire(operation)
    if (rectMatch != null) {
        val (a, b) = rectMatch.groups.drop(1).map { it!!.value.toInt() }
        turnOn(screen, a, b)
    }
    else {
        val rotationMatch = rotation.matchEntire(operation)
        val axis = rotationMatch!!.groups[1]!!.value
        val (index, pixels) = rotationMatch.groups.drop(2).map { it!!.value.toInt() }
        rotate(screen, axis, index, pixels)
    }
}

private fun screenToString(screen: MutableList<MutableList<Boolean>>): String {
    return screen.joinToString("\n") { row -> row.joinToString(" ") { if (it) "#" else "." } }
}

private fun partOne(inp: String): Int {
    val screen = MutableList(6) { MutableList(50) { false } }
    for (operation in inp.split('\n')) {
        applyOperation(screen, operation)
    }
    return screen.sumOf { row -> row.filter { it }.size }
}

private fun partTwo(inp: String): String {
    val screen = MutableList(6) { MutableList(50) { false } }
    for (operation in inp.split('\n')) {
        applyOperation(screen, operation)
    }
    return screenToString(screen)
}

fun main() {
    val inp = File("src/main/resources/day08.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
