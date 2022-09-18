import java.io.File

private fun supportsTLS(ip: String): Boolean {
    val negativeMatch = Regex("""\[\w*(\w)(\w)\2\1\w*]""")
    val positiveMatch = Regex("""\w*(\w)(\w)\2\1\w*""")
    if (negativeMatch.containsMatchIn(ip)) {
        return false
    }

    val match = positiveMatch.find(ip)
    if (match != null) {
        return match.groups[1]!!.value != match.groups[2]!!.value
    }

    return false
}

private fun partOne(inp: String): Int {
    return inp.split('\n').filter { supportsTLS(it) }.size
}

private fun getAllStuffs(part: String): List<String> {
    val ret = mutableListOf<String>()
    for (i in 0 until part.length - 2) {
        val slice = part.slice(i .. i + 2)
        if (slice[0] == slice[2] && slice[0] != slice[1]) {
            ret.add(slice)
        }
    }
    return ret
}

private fun supportsSSL(ip: String): Boolean {
    val rx = Regex("""\[(\w+)]""")
    val supernetParts = rx.split(ip)
    val filteredSupernets = supernetParts.flatMap { getAllStuffs(it) }

    val hypernetParts = rx.findAll(ip).map { it.groupValues.last() }.toList()
    val filteredHypernets = hypernetParts.flatMap { getAllStuffs(it) }

    return filteredSupernets.map { "${it[1]}${it[0]}${it[1]}" }.any { filteredHypernets.contains(it) }
}

private fun partTwo(inp: String): Int {
    return inp.split('\n').filter { supportsSSL(it) }.size
}

fun main() {
    val inp = File("src/main/resources/day07.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
