package xyz.mattyb.days

import java.io.File

open class BaseDay {

    fun readFileAsLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }

    fun test(test: Boolean?) = if (test == true) "-test" else ""
}