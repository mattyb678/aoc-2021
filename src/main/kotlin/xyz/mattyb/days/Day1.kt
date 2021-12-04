package xyz.mattyb.days

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Model.CommandSpec
import picocli.CommandLine.Spec
import java.util.concurrent.Callable

@Command(name = "day 1", aliases = ["d1", "d 1"])
class Day1 : Callable<Int> {

    @Spec
    lateinit var spec: CommandSpec

    @Command(name = "part 1", aliases = ["p 1", "p1"])
    fun part1(): Int {
        println("Day 1, Part 1")
        return 0
    }

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }
}