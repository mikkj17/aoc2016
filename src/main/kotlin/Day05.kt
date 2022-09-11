import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

private fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

private fun partOne(inp: String): String {
    var i = 0
    var password = ""

    while (true) {
        val digest = md5("$inp$i")
        if (digest.startsWith("00000")) {
            password += digest[5]
            if (password.length == 8) {
                return password
            }
        }
        i++
    }
}

private fun partTwo(inp: String): String {
    var i = 0
    val password = (0..7).map { 'z' }.toMutableList()

    while (true) {
        val digest = md5("$inp$i")
        if (digest.startsWith("00000")) {
            if (!digest[5].isDigit()) {
                i++
                continue
            }
            val position = digest[5].digitToInt()
            val character = digest[6]
            if (position < password.size && password[position] == 'z') {
                password[position] = character
            }
            if (!password.contains('z')) {
                return password.joinToString("")
            }
        }
        i++
    }
}

fun main() {
    val inp = File("src/main/resources/day05.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
