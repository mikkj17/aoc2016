import java.io.File

private fun partOne(inp: String): Int {
    val rx = Regex("""\s+(\d+)\s+(\d+)\s+(\d+)""")
    return inp.split('\n').filter { row ->
        val sortedSides = rx.matchEntire(row)!!.groups.drop(1).map {
            it!!.value.toInt()
        }.sorted()
        (sortedSides[0] + sortedSides[1]) > sortedSides[2]
    }.size
}

private fun partTwo(inp: String): Int {
    val rx = Regex("""\s+(\d+)\s+(\d+)\s+(\d+)""")
    val nums = inp.split('\n').map { row ->
        rx.matchEntire(row)!!.groups.drop(1).map {
            group -> group!!.value.toInt()
        }
    }
    var possibleCount = 0
    for (i in nums.indices step 3) {
        for (j in 0..2) {
            val sortedCol = listOf(nums[i][j], nums[i + 1][j], nums[i + 2][j]).sorted()
            if ((sortedCol[0] + sortedCol[1]) > sortedCol[2]) {
                possibleCount++
            }
        }
    }

    return possibleCount
}

fun main() {
    val inp = File("src/main/resources/day03.txt").readText().trimEnd()
    println(partOne(inp))
    println(partTwo(inp))
}
