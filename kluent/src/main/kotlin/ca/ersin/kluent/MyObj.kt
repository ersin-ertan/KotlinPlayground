package ca.ersin.kluent

import java.io.File

class MyObj {

    val a = listOf(1)
    val b = a

    val c = a.plus(2)

    fun a(i:Int) = i.toString()

    val d = 'd'

    val e = intArrayOf(1)

    val f = mapOf(1 to "a")

    fun ex():Nothing = throw RuntimeException()

    val g = File("")

}