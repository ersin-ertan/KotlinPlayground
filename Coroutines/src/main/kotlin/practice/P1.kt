package practice

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.NonCancellable.isActive
import kotlin.coroutines.experimental.suspendCoroutine

fun main(args:Array<String>) = runBlocking {
    //    val job: Job = launch(CommonPool) { countdown() }
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

fun oneToN(max:Int):IntRange = 1..max

fun comm() {
    launch(CommonPool) {}
    runBlocking { }
    runBlocking(CommonPool) { } // haven't seen this combo of coroutine builder

}

suspend fun sus() {
    delay(1)
    val job:Job = launch(CommonPool) {}
    job.cancel(Exception("Cancel"))
    val jobs = List(10) {
        // list of 10 jobs
        launch(CommonPool) { println("hi") }
    }
    // daemon like, any job that is running while the process ends
    // will get cancelled
    job.join() // will wait for child coroutine to finish

    // jobs that are cancelled must check it's status
    while (isActive) {
        // now if cancel is called, isActive will be false
        // dropping out of the while loop
    }
    try {// same semantic applies to jobs
    } finally {
        // will do this when canceled, but if the finally code
        // which is non blocking requires suspension
        run(NonCancellable) { /*finish coroutine specific stuff*/ }
    }

    // Timeout usually wrapped in a try-catch for the cancellationException when the timeout occurs
    // and computation has not finished
    withTimeout(100){}
}
