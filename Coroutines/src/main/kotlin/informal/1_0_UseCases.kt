package informal

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.File
import java.nio.CharBuffer
import kotlin.coroutines.experimental.buildSequence

/*
* @see <a href="https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md</a>
*
* What - generators/yield, async/await, composable/delimited continuations
*
* Goals - possible to use coroutines as wrappers for different existing async apis
* - no dependencies
* - do both async/await and generator blocks use cases
* */

// suspendable computation, suspend at one point, and later resume, possibly on another thread, coroutines calling each
// other, and communicating with data can form the machinery for cooperative multitasking

fun main(args: Array<String>) {
    println(generators().take(20).joinToString())
}

fun asyncComputation(){
    // async/await in c#)
    // async read to buffer, lambda when done
    // channel.read(buffer){ bytesRead -> process(buffer, bytesRead); outChannel.write(buffer){ outFile.close()}}
    // a callback within a callback, but no need to bass the buffer explicitly to callbacks

    launch(CommonPool){
//        val bytesRead = channel.read(buffer) // read, and write are suspending functions, resume when the call completes
//        process(buffer,bytesRead)
//        outChannel.write(buffer) // can be thought of it's own lambda
//        outFile.close()
    }
}
// because support for coroutines are generic, the launch, read, write are just library functions that working with coroutines
// launch being the coroutine builder with the context CommonPool, read/write are suspending functions which implicitly
// receive continuations(generic callbacks)

// explicitly passed callbacks tha have async calls in the middle of a loop can be tricky, but coroutines can do it simply
//launch(CommonPool){
//        val bytesRead = channel.read(buffer) // read, and write are suspending functions, resume when the call completes
//        if(bytesRead == -1) break
//        process(buffer,bytesRead)
//        outChannel.write(buffer) // can be thought of it's own lambda
//        outFile.close()
//}

fun futures(){
// library functions
//    val future = runAfterBoth(asyncLoadImage("a"), asyncLoadImage("b")){
//        original, overlay -> applyOverlay(original, overlay)
//    }

    // but with coroutines
//    val future = future{
//        val original = asyncLoadImage("a")
//        val overlay = asyncLoadImage("b")
//        // suspend while waiting for images to load, then
//        applyOverlay(original.await(), ovelay.await())
//    }
}

fun generators() =
    // lazy computed sequences(yield in c#/python) fibonacci example, potentially infinite
        // it's strength is supporting arbitrary control flow, such as while, if try-catch and all else, and composition
    buildSequence {
        yield(1)
        var cur = 1
        var next = 1
        while(true){
            yield(next)
            val tmp = cur + next
            cur = next
            next = tmp
        }
    }

/* allows yieldAll(seq) as a lib function, and buildSequence/yield as lib functions, simplifying lazy joins
val seq = buildSequence {
    yield(firstItem) // suspension point

    for (item in input) {
        if (!item.isValid()) break // don't generate any more items
        val foo = item.toFoo()
        if (!foo.isGood()) continue
        yield(foo) // suspension point
    }

    try {
        yield(lastItem()) // suspension point
    }
    finally {
        // some finalization code
    }
}*/


fun asyncUi(){
    // single ui threads, where modification from other threads are not allowed have some kind of primitive to move execution
    // back o the ui thread, android Activiy.runOnUiThread(), coroutines can solve callback hell
    // launch(Android){ try{ makeRequest(); display(result)} catch(e:Exception){ errorMessage()}
    // exception handling is done via language constructs
}

// more coroutine use cases
// channel based concurrency(goroutines, channels), actor based concurrency, background processes with occasional user interaction,
// communication protocols that implement each actor as a sequence rather than a state machine, web application workflows which
// register a user, validate email, log them in(a suspended coroutine may be serialized and stored in a DB)




















