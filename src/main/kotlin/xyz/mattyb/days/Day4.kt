package xyz.mattyb.days

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.ParentCommand
import picocli.CommandLine.Spec
import xyz.mattyb.MainCommand
import java.util.concurrent.Callable

@Command(name = "day 4", aliases = ["d 4", "d4"])
class Day4 : Callable<Int>, BaseDay() {

    @ParentCommand
    private val parent: MainCommand? = null

    @Spec
    lateinit var spec: CommandLine.Model.CommandSpec

    @Command(name = "part 1", aliases = ["p 1", "p1"])
    fun part1(): Int {
        println("Day 4, Part 1, test=${parent?.test}")
        var lines = readFileAsLines("src/main/resources/day4${test(parent?.test)}")
        val moves = lines.first().split(",").map { it.toInt() }
        lines = lines.drop(2)
        val boards = mutableListOf<Board>()
        while (lines.isNotEmpty()) {
            val board = lines
                .takeWhile { it.isNotBlank() }
                .toBoard()
            boards.add(board)
            lines = lines.drop(board.size + 1)
        }

        var completedBoard: Board? = null
        var finalMove: Int? = null

        outer@ for (move in moves) {
            for (board in boards) {
                board.mark(move)
                if (board.complete()) {
                    completedBoard = board
                    finalMove = move
                    break@outer
                }
            }
        }

        if (completedBoard != null && finalMove != null) {
            println("Answer: ${completedBoard.sum() * finalMove}")
        }

        return 0
    }

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return -1
    }

    private fun List<String>.toBoard(): Board {
        val rows = this.map { Line.Builder() }
        val cols = this.map { Line.Builder() }
        this.map { it.split(" ").filter { it.isNotBlank() } }
            .forEachIndexed { row, lst ->
                lst.forEachIndexed { col, s ->
                    rows[row].add(s.toInt())
                    cols[col].add(s.toInt())
                }
            }
        return Board(
            rows.map { it.build() },
            cols.map { it.build() }
        )
    }
}

data class Board(private val rows: List<Line>, private val cols: List<Line>) {

    val size: Int = rows.size

    fun sum(): Int {
        return rows.sumOf { it.sum() }
    }

    fun mark(num: Int) {
        rows.forEach { it.mark(num) }
        cols.forEach { it.mark(num) }
    }

    fun complete(): Boolean {
        return rows.any { it.allMarked() } || cols.any { it.allMarked() }
    }

}

class Line private constructor(
    private val nums: List<Int>
){
    private val marked: MutableList<Boolean> = nums.map { false } as MutableList<Boolean>

    fun mark(num: Int) {
        val idx = nums.indexOf(num)
        if (idx != -1) {
            marked[idx] = true
        }
    }

    fun sum(): Int {
        var sum = 0
        marked.forEachIndexed { idx, marked ->
            if (!marked) {
                sum += nums[idx]
            }
        }
        return sum
    }

    fun allMarked(): Boolean {
        return marked.all { it }
    }

    override fun toString(): String {
        return nums.toString()
    }

    data class Builder(
        private val nums: MutableList<Int> = mutableListOf()
    ) {
        fun add(num: Int) = apply { this.nums.add(num) }
        fun build() = Line(nums)
    }
}
