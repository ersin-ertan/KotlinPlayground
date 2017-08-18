package practice

import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) = runBlocking {
    val job: Job = launch(CommonPool) { countdown() }
//    job.join() // waits until child coroutine completes
    // not sure of the suspend vs job.join()
}

suspend fun countdown() {
    oneToN(1_000).forEach {
        // it -> is implicit
        if (it % 10 == 0)
            for (d in it downTo 0 step 10)
                print("$d ${if (d == 0) "\n" else " "}")
        else println(it)
    }
}

fun oneToN(max: Int): IntRange = 1..max
