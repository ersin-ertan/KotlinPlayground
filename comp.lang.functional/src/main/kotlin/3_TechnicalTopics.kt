// 3.0 Technical topics

// 3.1 Purity
// Pure functional programming language - perform all commutations via function application
// Or, those who allow side effects, which are computational effects of expression evaluation that persist after evaluation

// 3.2 Currying
// Functions with only a single argument. Ex f(a,b) will now be f(f`(b)(a) where f` is a function taking in a function

fun imperativeAdd(a:Int, b:Int) = a+b
fun functionalAdd(a:Int) = fun (b:Int) = a + b // this is currying, are isomorphic (AxB -> C) and (A -> (B -> C))

val usageI = imperativeAdd(1,2)
val usageF = functionalAdd(1)(2)

val l = (1..4).map{functionalAdd(it)}
val ll = (1..4).map{functionalAdd(it)(1)}

fun main(args: Array<String>) {
    l.forEach { it.pl() } // returns 4 Function1<Integer, Integer>
    ll.forEach { it.pl() } // returns 2, 3, 4, 5
} // every step of the function can be a variable

// 3.3 Monads
// From category theory, can be used to describe different features like(io, state manipulation, continuations, exception)

// 3.4 Parsers
// Program converting list of input tokens, usually characters into a a a value of appropriate type.
// Ex. Function to find the integer value representing by a string of digits.
// Parser generator tool, or combinator parsing(represented by functions and combined with small set of combinators, leading
// to parsers that resemble the grammar of the language being read(may use backtracking)

// 3.5 Strictness
// Strict - arguments to a function are evaluated before it is invoked, thus non terminating expression evaluations will
// not form the expression
// non strict - arguments to a function are not evaluated until values are actually required

// 3.6 Performance
// 3.7 Applications