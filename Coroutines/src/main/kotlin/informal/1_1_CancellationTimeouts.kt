package informal

import kotlinx.coroutines.experimental.*

fun cancellingExecution() = runBlocking{
// large applications need finer-grained control of cancellation, launch returns a Job to be cancelled
    val job = launch(CommonPool){
        repeat(1000){ i ->
            println("sleeping $i...")
            delay(200L)
        }
    }
    delay(1300L)
    println("main: tired of waiting, cancelling job")
    job.cancel()
    delay(1300L)
    println("quitting")
}

fun cooperativeCancellation() = runBlocking{
    // coroutine code has to cooperate to be cancellable, all suspending functions in kotlinx.coroutines are cancellable
    // they check for cancellation of the corouine and throw CancellationException when cancelled, but if it's working
    // on coputation, and doesn't check for cancellation, it cant' be cancelled
    val job = launch(CommonPool){
        var nextPrintTime = System.currentTimeMillis()
        var i = 0
        while(i < 10){
            val currentTime = System.currentTimeMillis()
            if(currentTime >= nextPrintTime){
                println("sleeping ${i++}..")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main: tired of waiting, cancelling job - but it's not checking cancellation, so it won't quit")
    job.cancel()
    delay(1300L)
    println("quitting")
}

fun computationCodeCancellable() = runBlocking<Unit> {
// there are two ways to make computation cancellable, periodically invoking suspending function, yield(), or
// explicitly checking the cancellation status
    val job = launch(CommonPool) {
        var nextPrintTime = 0L
        var i = 0
                // how often is this checked?
        while (isActive) { // cancellable computation loop, isActive is a property inside coroutines via CoroutineScope
            val currentTime = System.currentTimeMillis()
            if (currentTime >= nextPrintTime) {
                println("sleeping ${i++} ...")
                nextPrintTime = currentTime + 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting! flipping isActive property with cancel()")
    job.cancel() // cancels the job, flips isActive
    delay(1300L) // delay a bit to see if it was cancelled....
    println("main: see no more output, now I can quit.")
}

fun closingResourcesWithFinally() = runBlocking<Unit> {
// cancellable suspending functions throw CanccellationException on cancellation, which can be handled with try catch
// and kotlin's use function executions for finalization when cancelled
    val job = launch(CommonPool) {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("I'm running, but job.cancel() has been called")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    delay(1300L) // delay a bit to ensure it was cancelled indeed
    println("main: Now I can quit.")
}

fun runNonCancellableBlock() = runBlocking{
    // suspending functions in the finally block will throw a CancellationException, since the coroutine running the code
    // is cancelled. Usually closing a file, cancelling job, closing a channel, are non blocking. But a rare case may require
    // a suspend, thus wrap it in run(nonCancellable){}

    val job = launch(CommonPool){
        try {
            repeat(1000){ i ->
                println("sleeping $i...")
                delay(400L)
            }
        } finally {
            run(NonCancellable){
                println("running non cancellable")
                delay(1000L) // delay is only allowed in coroutines
                println("delayed for 1 sec in non cancellable code")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!, cancelling")
    job.cancel() // cancels the job
    delay(1300L) // delay a bit to ensure it was cancelled indeed
    println("main: Now I can quit.")
}

fun timeout()=runBlocking{
    // will throw TimeoutException, by withTimeout, a private subclass of CancellaionException. so use try catch
    try {
        withTimeout(1200L){
            repeat(1000){i ->
                println("sleeping $i...")
                delay(400L)
            }
        }
    }catch (ce:CancellationException){ println("caught cancellationException")}
}


fun main(args: Array<String>) {
//    cancellingExecution()
//    cooperativeCancellation()
//    computationCodeCancellable()
//    closingResourcesWithFinally()
//    runNonCancellableBlock()
//    timeout()
}














































