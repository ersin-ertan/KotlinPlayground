package functionalDataValidation

import p

// in which we overcome restrictions or arity

// our address must account for 5 instead of the the 2 items

// We can use applicative functors, applicative builder patterns, and recursive data structures like HList

// define a new data type

data class J<A, B>(val
                   a:A, val b:B)

fun main(args:Array<String>) {
//    val pair = J(29, "Acacia Road")
//    val triple = J(J(30, "Acacia Road"), "ABC 123")
//
//    assign(pair, triple)

    readAddress3(FormData3()).map { it.toString().p() } // using toString to emphasize that the it value is
    // the Address3

}

data class Address3(val num:Int, val adr:String, val zip:String)

private fun <A, B, C> assign(pair:J<A, B>, triple:J<J<A, B>, C>) {


    val address = when {
        pair.a is Int && pair.b is String -> Address2(pair.a, pair.b)
        else -> null
    }

    println(address)


    val address3 = if (triple.a.a is Int && triple.a.b is String && triple.b is String) {
        Address3(triple.a.a, triple.a.b, triple.b)
    } else null

    println(address3)
}

// redefine result

sealed class ResultRedefined<A> {

    fun <B> flatMap(r:(A) -> ResultRedefined<B>):ResultRedefined<B> = // implement the pattern matching here
            when (this) {
                is Pass3 -> r(this.value)
                is Fail3 -> TODO()
            }

    fun <B> J(that:ResultRedefined<B>):ResultRedefined<*> = // should be ResultRedefined<J<A,B>>
            if (this is Pass3<A> && that is Pass3<B>) Pass3(J(this.value, that.value))
            else if (this is Fail3 && that is Pass3<B>) Fail3(this.msg)
            else if (this is Pass3<A> && that is Fail3) Fail3(that.msg)
            else/* (this is Fail3 && that is Fail3)*/ Fail3((this as Fail3).msg + (that as Fail3).msg)

    inline infix fun <reified B> map(func:(A) -> B) = if (this is Pass3<A>) Pass3<B>(func(this.value)) else Fail3(listOf("map failed"))

}

class Pass3<A>(val value:A):ResultRedefined<A>()
class Fail3(val msg:List<String>):ResultRedefined<Nothing>() // Nothing or Unit


class FormData3 {
    fun getField(name:String):ResultRedefined<String> = when (name) {
        "number" -> Pass3<String>("1")
        "street" -> Pass3("King St")
        "zipCode" -> Pass3("123 ABC")
        else -> TODO()
    }
}

private fun <A> rule(f:(A) -> ResultRedefined<A>) {}
private typealias Rule3<A, B> = (A) -> ResultRedefined<B>


val readNumber3:Rule3<FormData3, Int> = { form:FormData3 ->
    form.getField("number").flatMap { Pass3(Integer.parseInt(it)) }
}

val readAddress3 = { form:FormData3 ->
    val n = readNumber3(form)
    val s = form.getField("street")
    val z = form.getField("zipCode")


    (n.J(s.J(z))) map {
        val typedValue = it as J<Int, J<String, String>> // not a clean way, forced cast
        Address3(typedValue.a, typedValue.b.a, typedValue.b.b)
    }
}

// end of sprint 4
// we can validate with any number of parallel rules
// applicative builders would let us use regular functions