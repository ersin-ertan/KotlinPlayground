package essentialScala

// Structural Recursion

// Goal is to transform algebraic data types
// two subb patterns pattern matching and polymorphism


// Pattern matching
// sum type, you have a case for each or
sealed class A {
    fun doSomething():H = when (this) {
        is B -> doB(d, e) // cas B(d, e) -> doB(d,e) // not sure what the values/properties are inputted for
        is C -> doC(f, g)
    }

}

fun doB(d:D, e:E):H = H()
fun doC(f:F, g:G):H = H()

class B(val d:D, val e:E):A() {
}

class C(val f:F, val g:G):A() {
}

class D
class E
class F
class G
class H

// Polymorphism
interface AA {
    fun doSomething():H
}

class BB(val d:D, val e:E):AA {
    override fun doSomething():H = doB(d, e)
}

// calculation example

sealed class Calculation1 {
    fun add(value:Int):Calculation1 =
            when (this) {
                is Success1 -> Success1(v + value)
                is Failure1 -> Failure1(msg)
            }
}

class Success1(val v:Int):Calculation1()
class Failure1(val msg:String):Calculation1()

// Summary
// Processing algebraic data types immediately follows from the structure of the data
// Can choose between pattern matching and polymorphism
// Pattern matching (within the base trait) is usually preferred