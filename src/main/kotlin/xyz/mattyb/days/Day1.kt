package xyz.mattyb.days

import picocli.CommandLine.Command
import picocli.CommandLine.Model.CommandSpec
import picocli.CommandLine.Spec
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger

@Command(name = "day 1", aliases = ["d1", "d 1"])
class Day1 : Callable<Int>, BaseDay() {

    @Spec
    lateinit var spec: CommandSpec

    @Command(name = "part 1", aliases = ["p 1", "p1"])
    fun part1(): Int {
        println("Day 1, Part 1")
        val lines = readFileAsLines("src/main/resources/day1")
        val count = lines.map { it.toInt() }
            .windowed(2, 1)
            .count { (a, b) ->  b > a }
        print("Answer: $count")
        return 0
    }

    @Command(name = "part 2", aliases = ["p 2", "p2"])
    fun part2(): Int {
        println("Day 1, Part 1")
        val lines = readFileAsLines("src/main/resources/day1")
            .map { it.toInt() }
        val count = lines.windowed(3)
            .windowed(size = 2, step = 1)
            .count { (a, b) -> b.sum() > a.sum() }
        print("Answer: $count")
        return 0
    }

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }
}