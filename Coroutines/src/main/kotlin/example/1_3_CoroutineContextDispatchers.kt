package example

import kotlinx.coroutines.experimental.*
import kotlin.coroutines.experimental.CoroutineContext

// launch(CommonPool), async(CommonPool), run(NonCancellable); CommonPool and NonCancellable are coroutine contexts
fun log(msg:String) = println("[${Thread.currentThread().name}] $msg")

fun main(args:Array<String>) {

//    dispatchThreads()
//    unconfinedAndConfined()
//    debuggingCoroutinesAndThreads()
//    jobInContext()
//    childrenOfCoroutine()
//    combiningContexts()
//    namingCoroutinesForDebugging()
//    cancellationViaExplicitJob()

}

// Dispatchers and threads - context includes a coroutine dispatcher, determining what thread/s the coroutine uses for execution
// It can confine execution to a specific thread, dispatch to a thread pool, or let it run unconfined
fun dispatchThreads() = runBlocking {
    val jobs = arrayListOf<Job>()

    // main thread
    jobs += launch(Unconfined) { println("Unconfined\t\t\tThread: ${Thread.currentThread().name}") }

    // context of parent, thus runBlocking coroutine
    jobs += launch(coroutineContext) { println("coroutineContext\tThread: ${Thread.currentThread().name}") }

    // disppatched to ForkJoinPool.commonPool (or equivalent)
    jobs += launch(CommonPool) { println("CommonPool\t\t\tThread: ${Thread.currentThread().name}") }

    // get its own new thread
    jobs += launch(newSingleThreadContext("newSTC")) { println("newSTC\t\t\t\tThread: ${Thread.currentThread().name}") }

    jobs.forEach { it.join() }
}

// unconfined vs confined dispatcher
// unconfined coroutine dispatcher starts coroutines in the caller thread, but only until the first suspension point.
// After suspension, it resumes in the thread that is fully determined by the suspending function that was invoked.
// Appropriate when coroutine does not consume CPU time nor updates any shared data like ui(confined to specific thread)

// coroutineContext property is available inside the coroutine block via CoroutineScope interface, and references a context
// of the particular coroutine. A parent context can be inherited, the default context of runBlocking, is confined to
// be the invoker thread, thus inheriting it has the effect of confining execution to this thread with predictable FFO scheduling

fun unconfinedAndConfined() = runBlocking {
    val jobs = arrayListOf<Job>()
    jobs += launch(Unconfined) {
        // work in main thread
        println("Unconfined\t\t\tWorking Thread: ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined\t\t\tAfter suspension Thread: ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) {
        // context of parent, runBlocking coroutine
        println("coroutineContext\tWorking Thread: ${Thread.currentThread().name}")
        delay(500)
        println("coroutineContext\tAfter suspension Thread: ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}

fun debuggingCoroutinesAndThreads() {
    // coroutines can suspend on one thread and resume on another with unconfined dispatcher or multi threaded dispatcher
    // like CommonPool. A single threaded dispatche might be hard to figure out what coroutine was doing what where when.
    // It's common to print thread name in log, with coroutines, the thread alone doesn't give much context, so kotlinx.coroutines
    // includes debugging

    // Run the following code with -Dkotlinx.coroutines.debug JVM option: @see https://stackoverflow.com/questions/32484449/how-to-define-default-jvm-arguments-in-idea

    runBlocking<Unit> {
        val a = async(coroutineContext) {
            log("I'm computing a piece of the answer")
            6
        }
        val b = async(coroutineContext) {
            log("I'm computing another piece of the answer")
            7
        }
        log("The answer is ${a.await() * b.await()}")
    }

    // three coroutines, main via runBlocking, and two corouines computing deferred values a and b, executing in the
    // context of runBlocking, confined to main thread. Output:
    /*[main @coroutine#2] I'm computing a piece of the answer
    [main @coroutine#3] I'm computing another piece of the answer
    [main @coroutine#1] The answer is 42*/

    // log function prints the name of the thread in square brackets, the identifier of current executing coroutine appended
    // id is consecutively assigned to all created coroutines when debugging
    // for more debugging, see newCoroutineContext

}

fun jobInContext() = runBlocking {
    // coroutine job is part of its context, the coroutine can retrieve it from its own context using coroutineContext[Job]
    println(("my job is ${coroutineContext[Job]}"))
    // my job is BlockingCoroutine{Active}@7530d0a
    // isActive in CoroutineScope is a shortcut for coroutineContext[Job]!!.isActive
}

fun childrenOfCoroutine() = runBlocking {
    // when coroutineContext of a acoroutine is used to launch another coroutine, the job of the new coroutine becomes
    // a child of the parent coroutines job. When the parent coroutine is cancelled, all of its children are recursively too

    val request = launch(CommonPool) {
        val job1 = launch(CommonPool) {
            println("job1: my own context and execute independently")
            delay(1000)
            println("job1: not affected by cancellation of the request")
        }

        val job2 = launch(coroutineContext) {
            try {
                println("job2: child of request coroutine")
                delay(1000)
                println("job2: am affected by cancellation of the request")
            } finally {
                println("job2: cancelled")
            }
        }

        job1.join(); job2.join()
    }
    delay(500)

    println("request.cancel()")
    request.cancel()

    delay(1000)

}


fun combiningContexts() = runBlocking {
    // coroutine context can be combined using + operator. The context on the right hand side replaces relevant entries
    // of the context on the left hand side. A job of the parent coroutine can be inherited while its dispatcher replaced
    val request = launch(coroutineContext) {
        // of run blocking
        // spawn cpu intensive child job in commonPool
        val job = launch(coroutineContext + CommonPool) {
            // this is the combination
            println("job: child of request coroutine, with dif dispatcher")
            // what is a dispatcher?
            delay(1000)
            println("job: won't execute this line if parent request is cancelled")
        }
        job.join()
    }
    delay(400)
    println("request.cancel()")
    request.cancel()
    delay(1000)
}

fun namingCoroutinesForDebugging() = runBlocking(CoroutineName("main")) {
    log("started main coroutine")

    val v1 = async(CommonPool + CoroutineName("v1coroutine")) {
        log("computing v1")
        delay(500)
        252
    }
    val v2 = async(CommonPool + CoroutineName("v2coroutine")) {
        log("computing v2")
        delay(1000)
        6
    }
    log("v1/v2 = ${v1.await() / v2.await()}")
}

fun cancellationViaExplicitJob() = runBlocking {
    /*
    Putting context, children, and jobs together. Our app has an object with a lifecycle, but the object is not a coroutine.
    android app, which launches various coroutines in the context of an activity to perform asynch operations to fetch and
    update data, do animations. All coroutines must be canceled when the activity is destroyed to avoid memory leaks.

    Coroutine lifecycles are managed via creating a job instance, which is tied to the lifecycle of the activity.
    A job instance is created using Job() factory function. All coroutines must be started with this job in their context,
    allowing a single invocation of Job.cancel to terminate them all.
    */

    val job = Job() // single job object to manage our lifecycle,  launch ten coroutines
    val coroutines = List(10) { i ->
        launch(coroutineContext + job) {
            delay(i * 200L)
            println("coroutine $i is done")
        }
    }
    println("launched ${coroutines.size} coroutines")
    delay(500L)
    println("cancelling job")
    job.cancel()
    delay(1000L)

    // only three print, others are cancelled, thus create a parent job per activity, and use child coroutines, cancel
    // will destroy them

}