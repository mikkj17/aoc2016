import java.io.File

private val test = """
    cpy 41 a
    inc a
    inc a
    dec a
    jnz a 2
    dec a
""".trimIndent()

private fun compute(inp: String, registers: MutableMap<String, Int>): Int {
    val copyPattern = Regex("""^cpy (\w+) (\w)$""")
    val incPattern = Regex("""^inc (\w)$""")
    val decPattern = Regex("""^dec (\w)$""")
    val jumpPattern = Regex("""^jnz (\w) (-?\d+)$""")
    val instructions = inp.split("\n")

    var instructionCount = 0
    while (true) {
        if (instructionCount >= instructions.size) {
            break
        }
        val instruction = instructions[instructionCount]

        val copyMatch = copyPattern.matchEntire(instruction)
        if (copyMatch != null) {
            val (from, to) = copyMatch.groupValues.slice(1..2)
            if (from.all { it.isDigit() }) {
                registers[to] = from.toInt();
            }
            else {
                registers[to] = registers[from]!!
            }
            instructionCount++
            continue
        }

        val incMatch = incPattern.matchEntire(instruction)
        if (incMatch != null) {
            val register = incMatch.groupValues[1]
            registers[register] = registers[register]!! + 1
            instructionCount++
            continue
        }

        val decMatch = decPattern.matchEntire(instruction)
        if (decMatch != null) {
            val register = decMatch.groupValues[1]
            registers[register] = registers[register]!! - 1
            instructionCount++
            continue
        }

        val jumpMatch = jumpPattern.matchEntire(instruction)
        if (jumpMatch != null) {
            val (register, jumpLength) = jumpMatch.groupValues.slice(1..2)
            val value = if (register.all { it.isDigit() }) register else registers.getOrDefault(register, 0)
            if (value != 0) {
                instructionCount += jumpLength.toInt()
            }
            else {
                instructionCount++
            }
        }
    }
    return registers["a"]!!
}

private fun partOne(inp: String): Int {
    return compute(inp, mutableMapOf())
}

private fun partTwo(inp: String): Int {
    return compute(inp, mutableMapOf(Pair("c", 1)))
}

fun main() {
    val inp = File("src/main/resources/day12.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
