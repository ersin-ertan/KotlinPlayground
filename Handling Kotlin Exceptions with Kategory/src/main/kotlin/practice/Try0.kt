package practice

import kategory.*

fun Any.p() = println(this)
fun p() = println()

class Computation<IN, OUT>(val input:IN, val computFunc:(IN) -> Result<OUT>) {
    fun getResult() = computFunc(input)
}

class Result<OUT>(val myResult:OUT)

fun computeTryInvoke(charNum:Int) =
        Try.invoke {
            val result = Computation<Int, Char>(charNum, { i -> Result(i.toChar()) }).getResult()
            val char = result.myResult

            if (char == 'A') char else throw Exception("Not 'A' Exception")
        }

fun main(args:Array<String>) {

//    computeTryInvokeEx()

//    computeTryMonadEx()

//    computeTryDirectEx()

//    computeTryPatternMatching()
}

fun computeTryInvokeEx() {

    val r0 = computeTryInvoke(65)
    val r1 = computeTryInvoke(10)

    p()
    r0.p() // Success(value=A
    r1.p() // Failure(exception=java.lang.Exception: Not A Exception)

    p()
    r0.getOrElse { println("Default value for r0: "); 'X' }.p() // since Success, will return A and not do else clause
    r1.getOrElse { println("Default value for r1: "); 'Y' }.p() // since Failure due to throw, will return 'Y'

    p()
    r0.map { it.toLowerCase() }.p() // Success(value=a)
    r1.map { it.toLowerCase() }.p() // Failure(...)

    p() // recover with another try
    r0.recoverWith { Try.pure('R') }.getOrElse { 'r' }.p() // A
    r1.recoverWith { Try.pure('S') }.getOrElse { 's' }.p() // S

    p() // recover with a value
    r0.recover { thr -> 'R' }.p() // Success(value=A)
    r1.recover { thr -> 'R' }.p() // Success(value=R)

    p()
    r0.fold({ th -> 'c' }, { str -> 'C' }).p() // if both are the same type then this is ok
    r1.fold({ th -> 'd' }, { str -> 'D' }).p()

    computeTryInvoke(1).fold({ t:Throwable -> 4 }, { i:Char -> i })

    // but when using different types for Failure string vs Success Int
    p()
    Try.invoke { if (true) 2 else throw Exception() }.fold({ t:Throwable -> 4 }, { i:Int -> i }).p() // 2
    Try.invoke { if (true) 2 else throw Exception() }.fold({ t:Throwable -> "a" }, { i:Int -> i }).p() // a, still runs
    // was showing an error but was also running, and now the error disappeared
}

fun computeTryMonadEx() {

    Try.monad().binding {
        // MonadContinuation
        val result = computeTryInvoke('A'.toInt())
        val char = result.getOrElse { 'F' }

        yields(char)
    }.p() // Success(value=A)

    Try.monad().binding {
        // MonadContinuation
        val result = computeTryInvoke(2)
        val char = result.getOrElse { 'F' }

        yields(char)
    }.p() // Success(value=F)
}

fun computeTryDirectEx() {

    computeTryInvoke(1)
            .recoverWith { Try.pure('B') }
            .flatMap { Try.pure(it.toLowerCase()) }.p() // Success(value=b)

    computeTryInvoke('A'.toInt())
            .recoverWith { Try.pure('B') }
            .flatMap { Try.pure(it.toLowerCase()) }.p() // Success(value=a)

    computeTryInvoke(2)
//            .recover { 4 } // both are the same
            .recoverWith { Try.pure(4) } // 4 with no problem
            .flatMap { Try.pure(it) }.p() // Success(value=4) // it reverts to any

    computeTryInvoke(1)
            .recoverWith { Try.pure("B") }
            .flatMap { char -> Try.pure(char) }


}

fun computeTryPatternMatching() {

    val result = computeTryInvoke(1)
    when (result) {
        is Try.Success -> result.value.p()
        is Try.Failure -> result.exception.message?.p() // Not 'A' Exception
    }

    val result1 = computeTryInvoke(65)
    when (result1) {
        is Try.Success -> result1.value.p() // A
        is Try.Failure -> result1.exception.message?.p()
    }
}