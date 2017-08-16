import kotlin.coroutines.experimental.buildSequence

// @see https://kotlinlang.org/docs/reference/coroutines.html?q=&p=0#experimental-status-of-coroutines

// A way to avoid blocking a thread and replace it with a cheaper and more controllable operation: suspension of a coroutine.

// async is simplified by allowing libraries to control the operation. It can be sequential in the lib, but async for us.
// Library wraps relevant parts of user code into callbacks, subscribe to relevant events, schedule execution on dif
// threads, and code remains as simple as sequential

// many ansc mechanisms can be implemented as libs using coroutines -
// async/await(C#), channels and select(Go), generators/yield(python C#)

// Blocking vs suspending
// suspension can be controlled by a user library, and can decide on what happens upon suspension

suspend fun doComp(){ // suspending function, the library may decide to proceed without suspension if result is available
    // can take parms, return values, but only be called from coroutines and other suspending functions.
    // every coroutine must have at least one suspending function, most have an suspending lambda

}

// simplified async() from kotlinx.coroutines
fun <T> async(block: suspend() -> T){}

// block param has a function type with suspending modifier, we pass a suspending lambda to async

// await() can be a suspending function, suspending a coroutine until computation is done and returns a result
// await() can be a suspending fuction, both async and await cannot be called from a regular function

interface Base{
    suspend fun foo()
}

class Derived:Base{ // suspending functions that are virtual must also have the suspend modifier too
    override suspend fun foo() {  }
}

// @RestricctsSuspension annotation
// extension functions and lambdas can also be marked with suspend, enabling DSL creation and extendable apis.. Some
// case may need the library author to prevent adding new ways to suspend a coroutine
// the annotation may be used, when a receiver class or interface R is annotated with it, all suspending extensions
// are required to delegate to either members of R or extensions to it. This gguarantees that all suspensions happen through
// calling members of R, of which the library author can fully control.
// Rare case of every suspension is handled, implementing generators through the buildSequence() func needs any suspending
// call in the coroutine calling yield or yieldAll and not any other function, which is why SequenceBuilder is annotated
// with @RestrictsSuspension


/**Inner workings - rough sense
 complete implementation through a compilation technique((no vm/os support), suspension through code transformation.
 every suspending function is transformed into a state machine, where states correspond to suspending calls.
 Before suspension, the next state is stored in a field of a compiler generated class along with relevant local variables
 on resumption of the coroutine, local variables are restored and the state machine proceeds from the state right after
 suspension.

A suspended coroutine can be stored and passed around as an object that keeps its suspended state and locals, using the
 type call a Continuation, corresponding to continuation passing style, suspending fuctions take an extra param of type
 Continuation under the hood.



 Standard Api
 - Language support(suspending functions)
 - Low level core apis in the kotlin standard lib
 - high level apis that can be sued directly in user code

 Low level api:kotlin.coroutines - small, never be used other than to create higher level libraries
 -kotlin.coroutines
    - createCoroutine()
    - startCoroutine()
    - suspendCoroutine()
 - kotlin.coroutines.intrinsics
    - suspendCoroutineOrReturn

 Generators api in kotlin.coroutines, the only application level functions in kotlin.coroutines are
 - buildSequence()
 - buildIterator()

 shepped within kotlin-stdlib because of sequence relation, these functions implement generators, to cheaply build a lazy
 sequence
 */
val fibonacciSeq = buildSequence {
    var a= 0
    var b= 0

    yield(1)

    while(true){
        yield(a+b)

        var tmp = a+b
        a=b
        b=tmp
    }
} // generates a lazy, potentially infinite fibonacci sequence by creating a coroutine yielding consecutive fibonacci
// numbers calling yield(), every step of the iterator executes another portion of the coroutine generating the next number.
// Allowing us to take any finite list of numbers out of the sequence, fibSeq.take(9).toList, using coroutines

val lazySeq = buildSequence {
    print("START ")
    for (i in 1..5) {
        yield(i)
        print("STEP ")
    }
    print("END")
}

// Print the first three elements of the sequence, but only takes 3, never printing 4,5 END
// lazySeq.take(3).forEach { print("$it ") }

// other high level apis kotlinx.coroutines, application level apis based on coroutines are released in a separate library
// covering, platform agnostic async programing wih kotlinx-coroutines-core for go like chhannels supporting select and
// primitives
// apis based on CompletableFuture from jdk8
// nonblocking io NIO based on jdk 7 and higher
// swing, javafx, rxjava
