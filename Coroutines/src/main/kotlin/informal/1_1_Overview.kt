package informal

/* language mechanisms enabling writing coroutines, and standard libraries that govern their semantics

 Terminology -
 Coroutine - an instance of suspendable computation, conceptually similar to a thread as it takes in a block of code
 and has a similar life cycle of created started, but is not bound to any thread. It may suspend on one and resume
 on another, and may complete with a result or exception

 suspending function - marked with suspend, may suspend execution without blocking the current thread, by invoking
 other suspending functions, cannot be invoked from regular code, only other suspending functions and lambdas
 ex await and yield are suspending functions defined in a library, standard library provides primitive suspending
 functions to define all other suspending functions

suspending lambda - block of ccode run in a coroutine, like and ordinary lambda expression but its functional type is
marked with suspend, like code blocks: launch, future, buildSequence
suspending function calls inside inline lambdas like apply block are allowed bot no in the noinline or crossinline inner
lambda expressions. A suspension is treated as a special kind of non local control transfer

suspending function type - for suspendinf gunctions and lambdas, like regular function types but with suspend.
suspend () -> Int is an example without arguments returning an Int

coroutine builder - a function with the argument ofa suspending lambda, that creates a coroutine and optionally
gives access to its result from some form. Ex. launch, future, buildSequence, that are defined in a library. The
standard lib provides primitve coroutine builders that are used to define all other coroutine builders

kotlin doesn't have keywords to define and start coroutines, because they are simply functions.

suspension point - during a coroutine where execution its execution may be suspended, syntactically it is an invocation
of a suspending function, but the actual suspension happens when the function invokes the standard library primitive

continuation - a state of the suspended coroutine at its suspension point, conceptually representing the rest of its
execution after the suspension point
buildSequence {
    for (i in 1..10) yield(i * i)
    println("over")
}
every time the coroutine is suspended at the suspending function yield(), the rest of its execcution is represented
as a continuation, so we have 10 continuations. The first continuation has the value of i=2, a coroutine that is
created but no started is represented by its initial continuation of type Continuation<Unit> which is the whole execution

in order to support all timpes of async apis, you must minimize parts hard coded into the compiler, thus it is only
responsible for suspending functions, suspending lambdas, and corresponding suspending function types
*/

/*
* Continuation interrface
* */


fun main(args: Array<String>) {
    for(i in 1..10) println(i*i)
}
