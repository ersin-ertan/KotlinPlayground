package practice

import kotlinx.coroutines.experimental.*

fun Int.fromMain() = println("${this} Main")
fun Int.fromNonBlockingCoroutine() = println("${this} Non Blocking")
fun Int.fromBlockingCoroutine() = println("${this} Blocking")
fun Int.startJob() = println("${this} Start Job")
fun Int.endJob() = println("${this} End Job")
fun Int.fromSuspendingFuction() = println("${this} Suspending Function")

fun main(args:Array<String>) {

//    nonBlockingCoroutine()
//    blockingMainThread()
//    blockingMainThreadWaitingForJobToFinish()
//    refactoringLaunchsLambda()
//    lightweight()
//    likeDaemonThreads()
//    coroutineCancellation()
}


fun nonBlockingCoroutine() {
    1.fromMain()

    launch(CommonPool) {
        // execution within is deterministic order
        2.fromNonBlockingCoroutine()
        3.fromNonBlockingCoroutine()
        4.fromNonBlockingCoroutine()
        5.fromNonBlockingCoroutine()
        6.fromNonBlockingCoroutine()
        7.fromNonBlockingCoroutine()
        8.fromNonBlockingCoroutine()
        9.fromNonBlockingCoroutine()
        10.fromNonBlockingCoroutine()
        11.fromNonBlockingCoroutine()
        12.fromNonBlockingCoroutine()
        13.fromNonBlockingCoroutine()
        14.fromNonBlockingCoroutine()
        15.fromNonBlockingCoroutine()
        16.fromNonBlockingCoroutine()
        17.fromNonBlockingCoroutine()
        18.fromNonBlockingCoroutine()
        19.fromNonBlockingCoroutine()
    }

//    (1..1000).forEach { print(it) }; println()
// not all non blocking coroutines will execute(no guarantee for any to execute), running multiple times will/may vary results
// Then, try uncommenting the simulated work

    20.fromMain()
}

fun blockingMainThread() {

    1.fromMain()
    2.fromMain()
    3.fromMain()

    runBlocking {
        4.fromBlockingCoroutine()
        5.fromBlockingCoroutine()
        6.fromBlockingCoroutine()
    }

    7.fromMain()
    8.fromMain()
    9.fromMain()
}

fun blockingMainThreadWaitingForJobToFinish() {

    (-2).fromMain()
    (-1).fromMain()
    0.fromMain()

    runBlocking {

        0.fromBlockingCoroutine()
        1.fromBlockingCoroutine()
        2.fromBlockingCoroutine()

        val listOfJobs = arrayListOf<Job>()
        (3..9).forEach {
            // execution of jobs is non deterministic order
            listOfJobs.add(
                    launch(CommonPool) {
                        // these are child coroutines
                        it.startJob()
                        delay(it * 1000L)
                        (it * 10).endJob()
                    })
        }

        delay(100)
        // job launching takes time, delay will prevent 10,11,12 from executing before Start Job; try commenting out

        10.fromBlockingCoroutine()
        11.fromBlockingCoroutine()
        12.fromBlockingCoroutine()

        listOfJobs.forEach { it.join() }
        // the blocking coroutine doesn't wait for jobs(child coroutines) to finish unless explicitly specified; try commenting out

        91.fromBlockingCoroutine()
        92.fromBlockingCoroutine()
        93.fromBlockingCoroutine()
    }

    94.fromMain()
    95.fromMain()
    96.fromMain()
}

fun refactoringLaunchsLambda() {

    1.fromMain()

    launch(CommonPool) {
        // coroutine logic goes here but we want to extract it and still keep that coroutine scope to use/call other
        // suspending functions
        2.fromNonBlockingCoroutine()

        suspendingFuction()

        5.fromNonBlockingCoroutine()
    }

//    (10).fromMain()
    // if uncommented, will run after 1 and before 2

    Thread.sleep(2000)
    // will delay main enough allowing for launch and suspendingFunction to run

    6.fromMain()
}

suspend fun suspendingFuction() {

    3.fromSuspendingFuction()

    delay(1000) // this suspended function can now call other suspended functions

    4.fromSuspendingFuction()
}

fun lightweight() = runBlocking {

    1.fromBlockingCoroutine()

    println()

    // create one thousand coroutines
    val jobs = List(1_000) {
        launch(CommonPool) { delay(100); print("$it ") }
    }

    // will wait for each to finish, all are created in parallel else sequentially we wait for 1_000 * 100ms delay = 1 min 40 sec
    // order is not guaranteed
    jobs.forEach { it.join() }

    println(); println()

    3.fromBlockingCoroutine()
}

fun likeDaemonThreads() {

    1.fromMain()

    println()

    launch(CommonPool) {
        var count = 0
        while (true) {
            print("${count++}");
//            delay(20)
            // coroutine is running until main terminates, even after the last println
            // uncomment delay to allow the program to finish sleeping, print, and terminate without the coroutine output
        }
    }

    Thread.sleep(100)

    println("\n\n2.fromMain()\n")
}

fun coroutineCancellation() {

    1.fromMain()

    val job = launch(CommonPool) {
        var count = 0
        while (isActive) {
            print("${count++}")
        }
        println("\nisActive = $isActive")
    }

    Thread.sleep(100)

    job.cancel()

    println()

    // both isActive = false and 2.fromMain will run, there is no guarantee as to how long it takes to cancel, thus isActive
    // can be before 2.fromMain, or after, which means that counting will occur after 2.fromMain up until isActive = false
    2.fromMain()

    Thread.sleep(100)
    // by this point, both 2.fromMain and the coroutine has completed isActive = false, main will terminate after 3 is called

    println()

    3.fromMain()
}