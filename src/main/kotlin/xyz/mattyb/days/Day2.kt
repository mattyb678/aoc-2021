package xyz.mattyb.days

import picocli.CommandLine
import picocli.CommandLine.*
import xyz.mattyb.MainCommand
import java.util.concurrent.Callable

@Command(name = "day 2", aliases = ["d2", "d 2"])
class Day2 : Callable<Int>, BaseDay() {

    @ParentCommand
    private val parent: MainCommand? = null

    @Spec
    lateinit var spec: CommandLine.Model.CommandSpec

    @Command(name = "part 1", aliases = ["p 1", "p1"])
    fun part1(): Int {
        println("Day 2, Part 1, test=${parent?.test}")
        var horizontal = 0
        var vertical = 0
        val lines = readFileAsLines("src/main/resources/day2${test(parent?.test)}")
        lines.forEach {
            if (it.startsWith("forward")) {
                horizontal += "forward (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            } else if (it.startsWith("down")) {
                vertical += "down (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            } else if (it.startsWith("up")) {
                vertical -= "up (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            }
        }
        println("Result: " + (horizontal * vertical))
        return 0
    }

    @Command(name = "part 2", aliases = ["p 2", "p2"])
    fun part2(): Int {
        println("Day 2, Part 2, test=${parent?.test}")
        var horizontal = 0
        var vertical = 0
        var aim = 0
        val lines = readFileAsLines("src/main/resources/day2${test(parent?.test)}")
        lines.forEach {
            if (it.startsWith("forward")) {
                val x = "forward (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
                horizontal += x
                vertical += aim * x
            } else if (it.startsWith("down")) {
                aim += "down (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            } else if (it.startsWith("up")) {
                aim -= "up (\\d+)".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            }
        }
        println("Result: " + (horizontal * vertical))
        return 0
    }

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }
}