package practice

import kotlinx.coroutines.experimental.*

fun Int.fromMain() = println("${this} Main")
fun Int.fromNonBlockingCoroutine() = println("${this} Non Blocking")
fun Int.fromBlockingCoroutine() = println("${this} Blocking")
fun Int.startJob() = println("${this} Start Job")
fun Int.endJob() = println("${this} End Job")

fun main(args:Array<String>) {

//    nonBlockingCoroutine()
//    blockingMainThread()
//    blockingMainThreadWaitingForJobToFinish()
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