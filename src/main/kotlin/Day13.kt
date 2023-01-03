import java.io.File
import java.util.PriorityQueue
import kotlin.math.max

private typealias Position = Pair<Int, Int>

private val test = """
    10
""".trimIndent()

private fun compute(x: Int, y: Int, favoriteNumber: Int): Int {
    return x*x + 3*x + 2*x*y + y + y*y + favoriteNumber
}

private fun evenBits(n: Int): Boolean {
    return Integer.toBinaryString(n).count { it == '1' } % 2 == 0
}

private fun getNeighbours(position: Position): Set<Position> {
    val (x, y) = position.toList()
    return setOf(
        Pair(x, max(0, y - 1)),
        Pair(max(0, x - 1), y),
        Pair(x, y + 1),
        Pair(x + 1, y),
    ) - position
}

private fun dijkstra(favoriteNumber: Int, dst: Position): Int {
    val expanded = mutableSetOf<Position>()
    val queue = PriorityQueue<Pair<Position, Int>>(compareBy { it.second })
    queue.add(Pair(Pair(1, 1), 0))

    while (queue.isNotEmpty()) {
        val (pos, cost) = queue.poll()
        if (pos == dst) {
            return cost
        }

        if (pos in expanded) {
            continue
        }
        else {
            expanded.add(pos)
        }

        for (adjacent in getNeighbours(pos)) {
            val (x, y) = adjacent.toList()
            if (!evenBits(compute(x, y, favoriteNumber))) {
                continue
            }
            queue.add(Pair(adjacent, cost + 1))
        }
    }

    throw AssertionError("Impossible")
}

private fun partOne(inp: String, destination: Position): Int {
    val favoriteNumber = inp.toInt()
    return dijkstra(favoriteNumber, destination)
}

private fun bfs(favoriteNumber: Int): MutableMap<Position, Int> {
    val startingPos = Pair(1, 1)
    val distances = mutableMapOf(startingPos to 0)
    val queue = mutableListOf(startingPos)

    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()
        val distance = distances.getValue(pos)
        if (distance >= 50) {
            break
        }

        for (neighbour in getNeighbours(pos)) {
            val (x, y) = neighbour.toList()
            if (!evenBits(compute(x, y, favoriteNumber))) {
                continue
            }

            if (!distances.contains(neighbour)) {
                queue.add(neighbour)
                distances[neighbour] = distance + 1
            }
        }
    }

    return distances
}

private fun partTwo(inp: String): Int {
    val favoriteNumber = inp.toInt()
    return bfs(favoriteNumber).size
}

fun main() {
    val inp = File("src/main/resources/day13.txt").readText().trim()
    println(partOne(inp, Pair(31, 39)))
    println(partTwo(inp))
}
