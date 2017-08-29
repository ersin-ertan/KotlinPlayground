import java.time.Instant.now as Tn

fun main(args:Array<String>) {
    hard()
}

// Hard keywords - tokens are always interpreted as keywords, cannot be used as identifiers
fun hard() {

    Tn() // using as import
    var n:Number = 2
    try {
        println(((n as Int).compareTo(n))) // smart type cast
    } catch (e:Exception) {
    }

    val x:Double? = n as? Double // avoid throwing exception, use safe cast as? returning null
    println(x) // null

    var c:Int = 0
    while (true) {
        println(c++)
        if (c == 3) break
    }

    open class A {
        var n = 0

        constructor(int:Int)

        fun a() {}
        inner class B:A {
            constructor():super(3)
        }
    }

    c = 0
    while (true) {
        c++
        if (c != 6) continue
        println(c)
        break
    }

    fun fo() {
        for (i in 1..1) println(false)
    }

    fo()

    if (1 in 1..10) println(true)
    if (0 !in 1..10) println(true)

    val a:A? = null // constant representing an object reference not pointing to an object

    if (x is Number) {
    }
    if (x !is Double) {
    }

    // declares a class and its instance at the same time
    object:A(3) {
        var a = 0

        init {
            f()
            B().f()
        }

        fun f() {
            return super.a()// returns from the nearest enclosing function or anon function
        }

        inner open class B() {
            open fun f() {
                this@B.javaClass.simpleName.p() // both are B
                this.javaClass.simpleName.p()
                println()
                try {
                    C().f()
                } catch (e:Exception) {
                    when (e) {
                        is Exception -> 1
                        !is Number -> 2
                        is IllegalArgumentException -> 3
                        else -> 4
                    }
                }
            }

            inner class C:B() {
                override fun f() { // must have open on f() in B
                    this@B.javaClass.simpleName.p() // B and C
                    if (this@C.javaClass.simpleName.p().equals(this@B)) throw Exception()
                }
            }
        }
    }

    typeAlias()

}

class TheClass {
    inner open class TheeClass {
        inner class TheeeClass(val i:Int):TheeClass()
    }
}

typealias TeeeC = TheClass.TheeClass.TheeeClass

fun typeAlias() {
    val i = TheClass().TheeClass().TheeeClass(3)
    if (i is TeeeC) println(i.javaClass)

}

interface Interfa

// Soft keywords - act as keywords in applicable contexts, but as identifiers in other contexts

// Modifier keywords - tokens are keywords in modifier lists of declarations,  and can used as identifiers in other contexts

// Special identifiers - identifiers are defined by the compiler in specific contexts and regular identifiers in others

// Operators and special symbols