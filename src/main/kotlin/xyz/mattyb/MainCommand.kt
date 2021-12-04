package xyz.mattyb

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Model.CommandSpec
import picocli.CommandLine.Spec
import xyz.mattyb.days.Day1
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@Command(
    name = "aoc",
    mixinStandardHelpOptions = true,
    subcommands = [
        Day1::class
    ]
)
class MainCommand : Callable<Int> {

    @Spec
    lateinit var spec: CommandSpec

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }

}

fun main(args: Array<String>) : Unit = exitProcess(CommandLine(MainCommand()).execute(*args))