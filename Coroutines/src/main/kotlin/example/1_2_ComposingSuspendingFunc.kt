package example

import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

fun main(args:Array<String>) {
//    sequentialByDefault()
//    concurentWithAsync()
//    lazyAsync()
    asyncStyleFunctions()

}

fun asyncStyleFunctions() {
    // invoke functions async using a coroutine builder
    val timeTaken = measureTimeMillis {
        // initiation of async actions are done outside of a coroutine
        val one = asyncNetworkingRequest()
        val two = asyncComputation()

        // waiting for the result most either suspend or block
        runBlocking { println("Results: one = ${one.await()}, two = ${two.await()}") }
    }
    println("Time taken = $timeTaken")
}

// not suspending functions
fun asyncNetworkingRequest() = async(CommonPool) { networkRequest() }

fun asyncComputation() = async(CommonPool) { computation() }


// still not sure about this one
fun lazyAsync() = runBlocking {
    // coroutine starts only when teh result is needed by await or start function
    val timeTaken = measureTimeMillis {
        val one = async(CommonPool, CoroutineStart.LAZY) { networkRequest() }
        val two = async(CommonPool, CoroutineStart.LAZY) { computation() }
        println("Results: one = ${one.await()}, two = ${two.await()}")
        // one starts, we await completion then two starts, it is sequential but it's not the intended purpose
        // designed as a replacement for the standard lazy function, when computation of the value involves suspending functions
    }
    println("Time taken = $timeTaken")
}


fun concurentWithAsync() = runBlocking {
    // launch returns a job, not carrying the result
    // async returns a deferred, a non blocking future representing a promise to provide a result later
    // use await on a deferred value to get its eventual result, deferred is also a job, to be canceled
    val timeTaken = measureTimeMillis {
        val one = async(CommonPool) { networkRequest() }
        val two = async(CommonPool) { computation() }
        println("Results: one = ${one.await()}, two = ${two.await()}")
    }
    println("Time taken = $timeTaken")
}

fun sequentialByDefault() = runBlocking {
    val timeTaken = measureTimeMillis {
        val one = networkRequest()
        val two = computation()
        println("Results: one = $one, two = $two")
    }
    println("Time taken = $timeTaken")

}

suspend fun networkRequest():Int {
    println("starting one")
    delay(1000)
    return 1
}

suspend fun computation():Int {
    println("starting two")
    delay(1000)
    return 2
}