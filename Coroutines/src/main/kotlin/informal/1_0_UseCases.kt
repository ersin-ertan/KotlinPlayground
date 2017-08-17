package informal

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