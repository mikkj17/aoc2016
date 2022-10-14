import java.io.File
import java.util.PriorityQueue

private fun fillBots(inp: String): MutableMap<Int, PriorityQueue<Int>> {
    val inputPattern = Regex("""value (\d+) goes to bot (\d+)""")
    val bots = mutableMapOf<Int, PriorityQueue<Int>>()
    inputPattern.findAll(inp).forEach { input ->
        val (value, bot) = input.groups.drop(1).map { it!!.value.toInt() }
        if (!bots.containsKey(bot)) {
            bots[bot] = PriorityQueue(2)
        }
        bots[bot]?.add(value)
    }

    return bots
}

private fun getInstructions(inp: String): MutableMap<Int, Pair<Pair<String, Int>, Pair<String, Int>>> {
    val instructionPattern = Regex("""bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""")
    val instructions = mutableMapOf<Int, Pair<Pair<String, Int>, Pair<String, Int>>>()
    instructionPattern.findAll(inp).forEach { instruction ->
        val groups = instruction.groups.toList()
        val (bot, low, high) = groups.slice(1..5 step 2).map { it!!.value.toInt() }
        val lowMode = groups[2]!!.value
        val highMode = groups[4]!!.value

        instructions[bot] = Pair(Pair(lowMode, low), Pair(highMode, high))
    }

    return instructions
}

private fun compute(
    bots: MutableMap<Int, PriorityQueue<Int>>,
    instructions: MutableMap<Int, Pair<Pair<String, Int>, Pair<String, Int>>>,
    part: Int,
): Pair<Int?, MutableMap<Int, Int>?> {
    val outputs = mutableMapOf<Int, Int>()
    while (true) {
        val fullBots = bots.filter { it.value.size == 2 }
        if (fullBots.isEmpty()) {
            break
        }

        val fullBot = fullBots.keys.first()
        val microChips = bots[fullBot]!!

        if (part == 1 && microChips.containsAll(listOf(61, 17))) {
            return Pair(fullBot, null)
        }

        val low = microChips.first()
        val high = microChips.last()
        val (lowInstruction, highInstruction) = instructions[fullBot]!!
        val lowBot = lowInstruction.second
        val highBot = highInstruction.second
        if (lowInstruction.first == "output") {
            outputs[lowBot] = low
        }
        else {
            if (!bots.containsKey(lowBot)) {
                bots[lowBot] = PriorityQueue(2)
            }
            bots[lowBot]!!.add(low)
        }

        if (highInstruction.first == "output") {
            outputs[highBot] = high
        }
        else {
            if (!bots.containsKey(highBot)) {
                bots[highBot] = PriorityQueue(2)
            }
            bots[highBot]!!.add(high)
        }

        bots[fullBot]!!.clear()
    }

    return Pair(null, outputs)
}

private fun partOne(inp: String): Int {
    val bots = fillBots(inp)
    val instructions = getInstructions(inp)
    return compute(bots, instructions, 1).first!!
}

private fun partTwo(inp: String): Int {
    val bots = fillBots(inp)
    val instructions = getInstructions(inp)
    val outputs = compute(bots, instructions, 2).second!!

    return (0..2).map { outputs[it]!! }.reduce { acc, i -> acc * i }
}

fun main() {
    val inp = File("src/main/resources/day10.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
