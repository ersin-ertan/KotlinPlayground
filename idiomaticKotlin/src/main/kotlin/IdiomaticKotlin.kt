import com.sun.xml.internal.ws.commons.xmlutil.Converter
import java.time.Instant

// https://blog.philipphauer.de/idiomatic-kotlin-best-practices/

// java to kotlin

/*
* optional -> nullable types
* getter, setter, backinng field -> properties
* static utility class -> top level extension functions
* immutability, value objects -> data class with immutable properties, copy()
* fluent setter(wither) -> named and default arguments, apply()
* method chaining -> default arguments
* singleton -> object
* delegation -> delegated properties by
* lazy initialization(thread safe) -> delegated properties by:lazy()
* observer -> delegated properties by:Delegates.observable()
* */

// Functional programming pros: less error-prone, easier to understand and test, thread safe

fun useExpresions() {

    val name = "Joe"

    val message = when (name.toLowerCase()) {
        "bob" -> "hey bob"
        "joe" -> "hi joe"
        else -> "hello"
    }

    // can by if be replaced with when?

    val tryMessage = try {
        "text".let {
            // processing
        }
    } catch (e:Exception) {
        "defaultM"
    }
}

fun topLevelFunctions() {

    fun String.countXs():Int = length - replace("x", "").length

    "xFunxWithxKotlin".countXs()
}

class A(var a:Int = 0, var b:Int = 1, var c:Int = 2)

fun namedArgs() {

    val a = A(b = 3, c = 4)
}

fun apply() {

    val a = A().apply {
        a = 3
        b = 2
        c = 1
    }
}

fun dontOverloadForDefaultArgs() {

    fun doGeneric(a:Int, b:Boolean) {}

    fun doTrue(a:Int) {
        doGeneric(a, true)
    }

    fun useDefaultArgsInstead(a:Int, b:Boolean = true) {}

}

fun dealWithNull() {
    // avoid
    val a:A? = null
//    if(a == null) throw Exception()

    // use null safe call ?. or elvis operator ?:

    val b:Int = a?.b ?: throw Exception()


    // if-type
    // avoid
    if (a !is A) {
    }

    // use as?

    a as? A ?: throw Exception()
    a.b

    // if you have to !!.a, then you can structure in a better way
}

fun considerLet() {

    fun getOrder():Int? {return 3}
    fun calcBill(orderNum:Int):Double {return 4.0}

    val cost = getOrder()?.let { calcBill(it) }
    // or
    val cost1 = getOrder()?.let(::calcBill)
}

fun useValueObjects() {

    data class EmailAddress(val value:String)

    fun sendEmail(address:EmailAddress) {}
}

fun conciseMappingSingleExperssionFun(a:Int) = A(a) // just create the object here

// even better, useful if initialization is complex
fun A.specialA(a:Int) = A(a)

val a = A().specialA(1)

fun referToConstructorParamsInPropertyInit() {
    class Dont(a:Int) {
        private val myA:Int

        init {
            myA = a
        }
    }

    class ButRather(a:Int) {
        private val myA:Int = a
    }
}

// object for stateless interface implementation
object PairComparable:Comparable<Pair<String, Int>> {

    override fun compareTo(other:Pair<String, Int>):Int {
        return 1
    }

    fun doOther() {}
}

data class D(val only:Int, val vars:Int, val here:Int = 3)

fun destructuring():D {
    // returning multiple values from a function, define a data class(preferred), or use Pair (no semantics)
    return D(1, 2)
}

fun getDest() {
    // only allowed in local scope
    val (one, two, three) = destructuring()
    val (four, five) = destructuring() // size is not included thus not populated

    // and with maps
    val map = mapOf<String, Int>("hi" to 1)

    for ((key, value) in map) {
    }
}

fun adHocCreationOfStructs() {
    // usually we use data classes and object mapping to create JSON, but for quick and dirty

    val customer = mapOf(
            "name" to "John Smith",
            "age" to 34,
            "languages" to listOf("german", "english"),
            "address" to mapOf(
                    "city" to "London",
                    "street" to "Elephant St"
            )
    )

    println(customer)
}

fun main(args:Array<String>) {
    adHocCreationOfStructs()
}