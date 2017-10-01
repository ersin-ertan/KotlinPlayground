package functionalDataValidation

// in which we find a suitable model for validation

private class Address(val number:Int, val street:String)

// we don't have proof that the number is greater than zero, or the street is not empty
// we want to get all of the errors out at once

private interface Validator<T> {
    fun rules():List<Rule1<T>>
}

private interface Rule1<T> {
    fun test(input:T):Boolean
    val message:String
}

// this is the wrong approach, this is not functional. We are tying ourselves into an implementation
// by making a choice upfront

// functional programming boils everything down to the simplest layer and builds everything back up
// are the rules fail fast, disjunction, check one at a time?

// we only need one thing, a function type Rule<T> = T -> Result

private sealed class Result {
    infix fun and(that:Result):Result =
            when (this) {
                is Pass -> when (that) {
                    is Pass -> Pass
                    is Fail -> Fail(that.message)
                }
                is Fail -> when (that) {
                    is Pass -> Fail(message)
                    is Fail -> Fail(message + that.message)
                }
            }
}

private object Pass:Result()
private class Fail(val message:List<String>):Result()

private fun <T> rule(f:(T) -> Result) {}
private typealias Rule<T> = (T) -> Result

private val nonEmpty:Rule<String> = { value:String -> if (value.isEmpty()) Fail(listOf("Empty String")) else Pass }

private fun gte(min:Int):Rule<Int> = { value:Int -> if (value < min) Fail(listOf("Too small")) else Pass }

// we need to build a combinator to get the fail or the pass of the two rules

private val checkAddress:Rule<Address> = { address:Address -> gte(1)(address.number) and nonEmpty(address.street) }
// we need to define 'and' result1.and(result2)

// structural recursion, write pattern matching in the result, goto result and implement

// error messages are accumulated, we have a definition for check address, no frameworks

// A rule is a function, a result is an algebraic data type
// Construct complex rules by: composing simple rules(eg. using and creating higher order functions)
// write algorithms using structural recursion(aka pattern matching)