package example

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.repeat

/*
*  @see <a href="https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md</a>
*
*/

fun launch(){
    launch(CommonPool){ // launch is the coroutine builder, delay is a special suspending function, nonblocking
        // suspending the coroutine, only for coroutines
        delay(1000L)
        println("World")
    }
    println("Hello, ")
    Thread.sleep(2000L)
}

fun blockingAndNonBlocking(){
    runBlocking <Unit>{ // works like an adaptor used to start top level coroutine, regular code blocks
        // until the coroutine inside runBlocking is done
        launch(CommonPool){
            delay(1000L)
            println("world")
        }
        println("hello")
        delay(2000L) // non blocking delay
    }
}

fun waitingForAJob(){
    runBlocking {
        val job = launch(CommonPool){ // new coroutine with ref to the job
            delay(1000L)
            println("world")
        }
        println("hello")
        job.join() // wait until child coroutine completes
    }
}

fun extractFunctionRefactoring() = runBlocking {
    val job = launch(CommonPool){ doWorld() }
    println("hello")
    job.join()
}

suspend fun doWorld(){
    delay(1000L)
    println("world")
}

fun coroutinesLightweight() = runBlocking {
        var count = 0
    val jobs = List(100_000){
        launch(CommonPool){ // coroutines created
            delay(1000L) // initial delay
            print('.')
            if(++count%50 == 0) println() // doesn't always do 50 because parallel, not sequential
        }
    }
    jobs.forEach{ it.join() }
}

fun likeDaemonThreads() = runBlocking {
    launch(CommonPool){
        repeat(1000){ i ->
            println("sleeping $i...")
            delay(200L)
            // active coroutines don't keep the process alive, like daemon threads
        }
    }
    delay(1300L)
}

fun main(args: Array<String>) {
//    launch()
//    blockingAndNonBlocking()
//    waitingForAJob()
//    extractFunctionRefactoring()
//    coroutinesLightweight()
//    likeDaemonThreads()
}