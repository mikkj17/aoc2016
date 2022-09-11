import java.io.File

private fun getFrequencies(str: String): Map<Char, Int> {
    val frequencies = mutableMapOf<Char, Int>()
    for (char in str) {
        frequencies[char] = frequencies.getOrDefault(char, 0) + 1
    }
    return frequencies
}

private fun realRoom(room: MatchResult): Boolean {
    return getFrequencies(room.groups[1]!!.value.filterNot { it == '-' }).toList().sortedWith(compareBy(
        { -it.second }, { it.first }
    )).map { it.first }.take(5).joinToString("") == room.groups[3]!!.value
}

private fun partOne(inp: String): Int {
    val roomPattern = Regex("""([\w|-]+)-(\d+)\[(\w+)]""")
    return roomPattern.findAll(inp).filter { realRoom(it) }.map {
        it.groups[2]!!.value.toInt()
    }.sum()
}

private fun rotateChar(alphabet: List<Char>, char: Char, rotation: Int): Char {
    return alphabet[(alphabet.indexOf(char) + rotation) % alphabet.size]
}

private fun rotateString(alphabet: List<Char>, dashSpace: List<Char>, room: MatchResult): String {
    val sectorId = room.groups[2]!!.value
    return room.groups[1]!!.value.map {
        rotateChar(if (it in alphabet) alphabet else dashSpace, it, sectorId.toInt())
    }.joinToString("")
}

private fun partTwo(inp: String): Int {
    val roomPattern = Regex("""([\w|-]+)-(\d+)\[(\w+)]""")
    val alphabet = ('a'..'z').toList()
    val dashSpace = listOf('-', ' ')
    return roomPattern.findAll(inp).filter { realRoom(it) }.filter {
        "northpole-object-storage" == rotateString(alphabet, dashSpace, it)
    }.first().groups[2]!!.value.toInt()
}

fun main() {
    val inp = File("src/main/resources/day04.txt").readText().trim()
    val test = "qzmt-zixmtkozy-ivhz-343[zimth]"
    println(partOne(inp))
    println(partTwo(inp))
}
