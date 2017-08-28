// https://kotlinlang.org/docs/reference/idioms.html

// Idioms - collection of frequently used idioms in kotlin

// DTOs/Pojo
data class Customer(var name:String, val email:String)

// provides getters, setters for var, equals, hashCode, toString, copy, component1(), component2() for all properties


// Default values for function parameters
fun foo(a:Int = 0, b:String = "") {}


// list filtering
val positives = (-10..10).filter { it > 0 }


// String interpolation
val positivesS = "positives $positives"


// Instance Checks
val num = when (1) {
    is Number -> {
    }
    is Int -> {
    }
    else -> {
    }
}


// Traversing a map/list of pairs
val map = mapOf(1 to "a", 2 to "be")

fun f() {
    for ((k, v) in map) println("$k->$v")
}


// ranges
fun r() {
    for (i in 1..10) {
        i.toString() // closed range includes 10
    }
    for (i in 1 until 10) { // half open range, doesn't include 10
        i.toString()
    }
    for (i in 2..10 step 2) {
        i.toString()
    }
    for (i in 10 downTo 1) { // closed range, includes 1
        i.p()
    }
}


// Read only lists
val list = listOf<Int>(1, 2, 3)

// Read only map
val map1 = mutableMapOf<String, Int?>("a" to 1)

// map access
val v:Int? = map1["a"] // beware of needed null checks

fun setMapValue() {
    map1["a"] = v
}


// lazy property
val l:String by lazy { "l".plus("a").plus("z").plus("y") }


// Utility print - extension function
fun Any.p() = println(this)


// singleton creation
object MySingleton{
    val name = "Singleton"
}



