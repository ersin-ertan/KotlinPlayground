package essentialScala

// Sequencing Computation

// Goal is to use patterns for sequencing computation

// functional programming is about transforming values

// That is all you can do without introducing side effects

// a->b->c

// Three patterns: fold, map, flatMap

// Fold - generic conversion, all you do in fold is pass in the code on the right hand side, for any algebraic data type
// A -> B
// Abstraction over structural recursion

// take the bits of the doB() method as function parameters

sealed class A1 {
    fun fold(doBFun:(D1, E1) -> H1, doCFun:(F1, G1) -> H1):H1 =
            when (this) {
                is B1 -> doBFun(d, e)
                is C1 -> doCFun(f, g)
            }
}

class B1(val d:D1, val e:E1):A1()

class C1(val f:F1, val g:G1):A1()

class D1
class E1
class F1
class G1
class H1


// concrete example

// an invariant of Option
sealed class Result<T> {
    fun <R> fold(s:(T) -> R, f:R):R = when (this) {
        is Success2 -> s(value)
        is Failure2 -> f
    }
}

class Success2<T>(val value:T):Result<T>()
class Failure2<T>():Result<T>()

// how do we implement fold, with structural recursion pattern, and abstract out the arguments

// Fold is not always the best choice, because
// not all data is an algebraic data type(future, infinite lists)
// sometimes other methods are easier to use, with fold, we have to specify all the other use cases
// like option, flatmap...


// Result<A> but get user from database might not have a user for the given id
// convert user -> json the result is a Result<Json>
// Map is F<A> A->B F<B>

// Get user Result<User> and get the users order User -> Result<Order>
// FlatMap  F<A> A-> F<B> = F<B>
// Example getOrder(id:UserId):HttpResponse
// UserId->Result<User> then User->Result<Order> then Order->Json then Result<Json>->HttpResponse
//                   Flatmap here              Map here                         Fold here

// Summary
// Standard patterns for sequencing computations
// F<T> map (T->R) = F<R>
// F<T> flatmap(T->F<R>) = F<R>
// fold is a general transformation for algebraic data types
// you can teach monads in an introductory course
