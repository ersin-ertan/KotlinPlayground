// 2.1 Functional languages - what is a functional programming language

// programming style emphasizing expression evaluation, instead of command execution
// Expressions are formed by combining functions as the basic values

fun main(args: Array<String>) {
    imperative()
    functional()
}

fun imperative(){
    var n:Int = 0
    for (i in 1..4) n += i
    n.pl()
}

fun functional(){
    fun sum(intRange: IntRange) = intRange.fold(0, {acc, i -> acc+i })
    sum(1..4).pl()
    (1..4).sum().pl()
}

// 2.2 Motivations
// Software is becoming more complex. Well structured software is easy to write and debug, and reuse.
// Two main features are higher order functions and lazy evaluation to improve modularity.
// Manipulate lists, trees, numerical algorithms and implement alpha beta heuristic.
// Functional programs are smaller.