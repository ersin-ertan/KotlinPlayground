import kotlinx.coroutines.experimental.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

// @see https://kotlinlang.org/docs/tutorials/coroutines-basic-jvm.html

// coroutine is like a light weight thread, able to run in parallel, wait for each other
// and communicate. But, they are almost free, very cheap, able to create thousands
// with a small performance impact

fun firstCoroutine(){
    // starts new thread pool ForkJoinPool.commonPool(), one thread may have many coroutines
    // but will not complete because program will end before coroutine finishes
    launch(CommonPool){
        println("start launch common pool")
        delay(2000)
        println("end launch common pool")
    }
    Thread.sleep(2500) // program will wait 2.5 sec, enough for coroutine to finish
}


fun blockingCoroutine(){
    // program will wait till coroutine is finished
    runBlocking {
        println("start run blocking")
        delay(2000)
        println("end run blocking")
    }
}

fun tenKThreads(){
    val count = AtomicInteger()
    for (i in 1..10_000) thread(start = true) { count.addAndGet(i)}
    println("Number of counts ${count.get()}")
    // Number of counts 50_005_000 roughly 3-5 seconds
    Thread.sleep(6000)
}

fun tenKCoroutines(){
    val count = AtomicInteger()
    for (i in 1..10_000) runBlocking { count.addAndGet(i)}
    println("Number of counts ${count.get()}")
//    Number of counts 50_005_000 roughly subsecond
}

fun anotherWayToCall(){
    val deferred = (1..10_000).map{
        n ->
        async(CommonPool){
            delay(1000) // coroutines are run in parallel, else 10k delay of 1 sec
            n
        }
    }
    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum:$sum")
    }
}

// computation outside a coroutine must be modified via suspend
suspend fun coreComputation(n:Int):Int{
    println("start suspend coreComputation")
    delay(1000)
    println("end suspend coreComputation")
    return n
}

fun externalComputation(){
    async(CommonPool){
        coreComputation(1)
    }
    Thread.sleep(2000)

}

fun main(args : Array<String>) {

//    firstCoroutine()
//    blockingCoroutine()
//    tenKThreads()
//    tenKCoroutines()
//    anotherWayToCall()
//    externalComputation()
}