// atomic FU - idiomatic wat to use atomic operations in kotlin
// AtomicReference/Int/Long, but run it like AtomicReference/Int/LongFieldUpdater
// kotlin specific extensions(inline updateAndGet and getAndUpdate)
// compile tie dependencies only
// post compilation bytecode transformer, that declares all relevant field updaters for you


//private val top = atomic<Node?>(null) // must be declared as private val with initializer

// not working yet

class Node

fun main(args:Array<String>) {

}