package xyz.mattyb.days

import picocli.CommandLine.Command
import picocli.CommandLine.Model.CommandSpec
import picocli.CommandLine.ParentCommand
import picocli.CommandLine.Spec
import xyz.mattyb.MainCommand
import java.util.concurrent.Callable

@Command(name = "day 3", aliases = ["d 3", "d3"])
class Day3 : Callable<Int>, BaseDay() {

    @ParentCommand
    private val parent: MainCommand? = null

    @Spec
    lateinit var spec: CommandSpec

    @Command(name = "part 1", aliases = ["p 1", "p1"])
    fun part1(): Int {
        println("Day 3, Part 1, test=${parent?.test}")
        val lines = readFileAsLines("src/main/resources/day3${test(parent?.test)}")
        val bitCounts = bitCounts(lines)
        val gamma = calcGamma(bitCounts)
        val epsilon = calcEpsilon(bitCounts)
        println("Answer: ${gamma * epsilon}")
        return 0
    }

    @Command(name = "part 2", aliases = ["p 2", "p2"])
    fun part2(): Int {
        println("Day 3, Part 2, test=${parent?.test}")
        val orig = readFileAsLines("src/main/resources/day3${test(parent?.test)}")
        var lines = orig
        for (i in 0..lines[0].length) {
            val bitCounts = bitCounts(lines)
            val bitCount = bitCounts[i]
            val toUse = if (bitCount.zero > bitCount.one) 0 else 1
            lines = lines.filter { it[i].digitToInt() == toUse }
            if (lines.size == 1) break
        }
        val oxygenGenRating = lines[0].toInt(2)
        lines = orig
        for (i in 0..lines[0].length) {
            val bitCounts = bitCounts(lines)
            val bitCount = bitCounts[i]
            val toUse = if (bitCount.one >= bitCount.zero) 0 else 1
            lines = lines.filter { it[i].digitToInt() == toUse }
            if (lines.size == 1) break
        }
        val co2ScrubberRating = lines[0].toInt(2)
        println("Answer: ${oxygenGenRating * co2ScrubberRating}")
        return 0
    }

    private fun bitCounts(lines: List<String>): List<IntArray> {
        val diagNumLen = lines[0].length
        val bitCounts = (1..diagNumLen).map { intArrayOf(0, 0) }
        lines.forEach {
            it.forEachIndexed { i, c ->
                if (c.digitToInt() == 0) {
                    bitCounts[i].zero = bitCounts[i].zero.inc()
                } else {
                    bitCounts[i].one = bitCounts[i].one.inc()
                }
            }
        }
        return bitCounts
    }

    private fun calcGamma(pairsList: List<IntArray>): Int {
        return pairsList.map { if (it.zero > it.one) 0 else 1 }
            .joinToString("")
            .toInt(2)
    }

    private fun calcEpsilon(pairsList: List<IntArray>): Int {
        return pairsList.map { if (it.zero > it.one) 1 else 0 }
            .joinToString("")
            .toInt(2)
    }

    private var IntArray.zero
        get() = get(0)
        set(value) = set(0, value)

    private var IntArray.one
        get() = get(1)
        set(value) = set(1, value)

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }
}