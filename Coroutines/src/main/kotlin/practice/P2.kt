package practice

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

data class Counting(val initial:Int, val collection:ConcurrentLinkedQueue<Int> = ConcurrentLinkedQueue<Int>()):Collection<Int> by collection {

    private var count = 0

    fun autoAdd() = runBlocking {
        (1..100).forEach {
            delay(10) // delays the block lambda
            launch(CommonPool) {
//            delay(10) // print never occurs with delay
                collection.add(it)
                count++
            }
        }
        collection.forEach { print("$it ") }
        println()
    }
}

fun main(args:Array<String>) {
    val c = Counting(1)
    println("starting to block main thread with run blocking")
    c.autoAdd() // will block the main thread
    println("done c.autoAdd()")
}