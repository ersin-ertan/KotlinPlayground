package classesAndObjects.delegatedProperties

import gettingStarted.p
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


fun main(args:Array<String>) {
    lazyValue.p()
    lazyValue.p()
    lazyValue.p()

    perThread()
    perThreadClass()
    lazyV2.p()
    Thread.sleep(1000)
    lazyV2.p()

    val tllc = ThreadLocalLazyClass()

    repeat(3) {
        thread { println(tllc.threadId) }
    }

}

//Lazy - takes a lambda and returns an instance of Lazy<T> serving as a delegate for a lazy property via the first
// get() call, which executes the lazy(), caching the result for subsequent calls

val lazyValue:String by lazy {
    println("first computation")
    "The lazy value" // returned
}

class Lazy1() {
    val lazyPerThread:String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Instant.now().toString() }
}

fun perThreadClass() {
    val l = Lazy1()
    val ll = Lazy1()
    l.lazyPerThread.p()
    l.lazyPerThread.p()
    Thread.sleep(1000) // will yield different values
    ll.lazyPerThread.p()
    ll.lazyPerThread.p()
}

var timesAccessed = 0

// No this does not work, as the reference is still the last accepted value for all lazy thread safety modes
val lazyPerThread:ThreadLocal<String> by lazy(LazyThreadSafetyMode.NONE) {
    // Synchronized will allow one access
    // while PUBLICATION will allow 2(or more), NONE is 2(or more) but may give out different values upon concurrent
    // initial access, but will provide a single result across threads
    timesAccessed++
    "Times Accessed:${timesAccessed}".p()
    val tl = ThreadLocal<String>()
    tl.set(Instant.now().toString())
    tl
}

fun perThread() {
    "Time taken:${measureTimeMillis {
        Thread(Runnable {
            "${Thread.currentThread().name} ${lazyPerThread} ${lazyPerThread.get()}".p()
            Thread.sleep(500)
            "${Thread.currentThread().name} ${lazyPerThread} ${lazyPerThread.get()}".p()
        }, "A").start()
        Thread(Runnable {
            "${Thread.currentThread().name} ${lazyPerThread} ${lazyPerThread.get()}".p()
            Thread.sleep(500)
            "${Thread.currentThread().name} ${lazyPerThread} ${lazyPerThread.get()}".p()
        }, "B").start()
    }}".p()
//    sleep(1000)
}

// the evaluation of lazy properties are synchronize( the value is computed only in one thread, all other threads will
// see the same value), LazyThreadSafetyMode.PUBLICATION may be passed to allow per thread init, or
// LazyThreadSafetyMode.NONE, for no thread safety guarantees/overhead

val lazyV2:String by lazy() { getInstant() } // doesn't work

fun getInstant():String = Instant.now().toString()

//val threadLocalLazy by ThreadLocalDelegate<Lazy>(lazy { Instant.now().toString() })

// see thread local delegate file

class ThreadLocalLazyClass {
    val threadId by threadLocalLazy { Thread.currentThread().id }
}
