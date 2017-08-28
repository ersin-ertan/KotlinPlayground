import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

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
object MySingleton {
    val name = "Singleton"
}


// if not null
val files = File("test").listFiles()

fun printFiles() {
    println(files?.size)

    // if not null and else
    println((files?.size ?: "empty"))
}


// executing statement if null
val value = map[1] ?: throw IllegalStateException("no value in map")

// execute if not null
fun notNull() {
    val value = null
    value?.let { println("not null") }
}


// return on when
fun ret(i:Int) = when (i) {
    1 -> 1
    2 -> 2
    else -> throw IllegalArgumentException("unknown ret param value")
}


// try / catch
fun test() {
    val res = try {
    } catch (e:ArithmeticException) {
    }
    res.toString()
}


// if expression
fun ifExp(i:Int) {
    val res = if (i > 1) "greater"
    else if (i < 1) "less"
    else "equal"
}


// builder style usage of methods that return unit
fun arrayOfMinusOnes(size:Int):IntArray {
    return IntArray(size).apply { fill(-1) }
}

val array = arrayOfMinusOnes(3).get(1)


// single expression functions
fun theAnswer() = 43 // type is inferred


// calling multiple methods on an object instance 'with'
class Turtle {
    fun penDown() {}
    fun penUp() {}
    fun turn(degrees:Double) {}
    fun forward(pixels:Double) {}
}

val myTurtle = Turtle()
fun drawBox() {
    with(myTurtle) {
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }
}


// java 7's try with resources
fun tryWRes() {
    val stream = Files.newInputStream(Paths.get("/my/location/file.txt"))
    stream.buffered().reader().use { reader -> println(reader.readText()) }
}


// convenient form for generic functions that require generic type info
inline fun <reified T:Any> Gson.fromJson(json:Gson.JsonElement):T = this.fromJson(json, T::class.java)


// consuming a nullable boolean
fun consumeBoolean() {
    val b:Boolean? = null
    if (b == true) {
    } else {
    } // b is false, OR null
}
