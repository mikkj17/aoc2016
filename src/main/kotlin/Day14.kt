import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

private val test = """
    abc
""".trimIndent()

private data class Key(val index: Int, val hash: String, val tripleChar: Char)

private fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

private fun findTriplet(hash: String): Char? {
    for (window in hash.windowed(3)) {
        if (window.toSet().size == 1) {
            return window.first()
        }
    }

    return null
}

private fun hash(value: String, nTimes: Int): String {
    var hash = md5(value)
    repeat(nTimes) { hash = md5(hash) }
    return hash
}

private fun compute(inp: String, hashFunction: (String) -> String): Int {
    val possibleKeys = mutableListOf<Key>()
    val keys = mutableListOf<Key>()
    var i = 0
    while (keys.size < 64) {
        val hash = hashFunction("$inp$i")
        val keysToRemove = mutableListOf<Key>()
        for (key in possibleKeys) {
            if (i - key.index <= 1000) {
                if (key.tripleChar.toString().repeat(5) in hash) {
                    keys.add(key)
                    keysToRemove.add(key)
                }
            }
        }
        keysToRemove.forEach { possibleKeys.remove(it) }
        val tripleChar = findTriplet(hash)
        if (tripleChar != null) {
            possibleKeys.add(Key(i, hash, tripleChar))
        }
        i++
    }
    return keys[63].index
}

private fun partOne(inp: String): Int {
    return compute(inp) { hash(it, 0) }
}

private fun partTwo(inp: String): Int {
    return compute(inp) { hash(it, 2016) }
}

fun main() {
    val inp = File("src/main/resources/day14.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
