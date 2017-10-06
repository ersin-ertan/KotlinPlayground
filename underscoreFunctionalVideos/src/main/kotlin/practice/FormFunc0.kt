package practice

// my version is an incomplete example, I had problems using rule() and with Type<out B> returns
// https://github.com/davegurnell/functional-data-validation/blob/master/code/sprint4/src/main/scala/Main.scala

// be sure to look at, for an example of applicative functors and applicative builders
// https://github.com/davegurnell/functional-data-validation/blob/master/code/sprint4b/src/main/scala/Main.scala


// -------------------------------------------------- Validation Library

class J<A, B>(val a:A, val b:B) // arbitrary arity using a simple HList-Like recursive data structure `J`

sealed class Result<A> {

    fun <B> map(func:(A) -> B) = with(this) {
        when (this) {
            is Pass -> Pass(func(value))
            is Fail -> Fail(errorMsgList.plus("map fail"))
        }
    }

    fun <B> flatMap(rule:Rule<A, B>) = with(this) {
        when (this) {
            is Pass -> rule(value)
            is Fail -> Fail(errorMsgList.plus("flatmapFail"))
        }
    }

    fun <B, C> and(that:Result<B>, func:(A, B) -> C) = with(this) {
        when {
            this is Pass<A> && that is Pass<B> -> Pass(func(value, that.value))
            this is Pass<A> && that is Fail -> Fail(that.errorMsgList.plus("and fail"))
            this is Fail && that is Pass<B> -> Fail(errorMsgList.plus("and fail"))
            this is Fail && that is Fail -> Fail(errorMsgList.plus("and fail").plus(that.errorMsgList.plus("and fail")))
            else -> throw IllegalStateException("Result and not Pass or Fail")
        }
    }

    fun <B> J(that:Result<B>):Result<out J<A, B>> = this.and(that, ::J) // same as { a:A, b:B -> J(a, b) }
}

class Pass<A>(val value:A):Result<A>()
class Fail(val errorMsgList:List<String>):Result<Nothing>() // what is the return value

typealias Rule<A, B> = (A) -> Result<out B>

class RuleOps<A, B>(val rule:Rule<A, B>) {

    fun <C> map(func:(B) -> C):Rule<A, C> = { a:A -> rule(a).map(func) }
    fun <C> flatMap(rule2:Rule<B, C>):Rule<A, C> = { a:A -> rule(a).flatMap(rule2) }
    fun <C, D> and(rule2:Rule<A, C>, func:(B, C) -> D):Rule<A, D> = { a:A -> rule(a).and(rule2(a), func) }
    fun <C> J(that:Rule<A, C>):Rule<A, J<B, C>> = this.and(that, { a, b -> J(a, b) })
}

fun <A> rule():Rule<A, A> = { input:A -> Pass(input) }

// -------------------------------------------------- Application Code

data class Address(val number:Int, val street:String, val zipCode:String)

fun createAddress(input:J<J<out Int, out String>, out String>):Address = with(input) { Address(a.a, a.b, b) }

typealias FormData = Map<String, String>

// -------------------------------------------------- Validating Existing Addresses

val nonEmpty:Rule<String, String> = { s:String -> if (s.isEmpty()) Fail(listOf("Empty string")) else Pass(s) }
val initialCap:Rule<String, String> = { s:String -> if (s.first().isUpperCase()) Pass(s) else Fail(listOf("No initial cap")) }

fun capitalize(s:String):String = s.first().toUpperCase() + s.substring(1)
fun gte(min:Int) = { num:Int -> if (num < min) Fail(listOf("Num too small")) else Pass(num) }

val checkNumber:Rule<Address, Int> = { address:Address -> Pass(address).map { it.number }.flatMap(gte(1)) }

val checkNumber1:Rule<Address, Int> = { address:Address -> Pass(address).map { it.number }.flatMap(gte(1)) }

val checkStreet:Rule<Address, String> = { address:Address -> Pass(address).map { it.street }.flatMap(nonEmpty).map { it.capitalize() } }
val checkZip:Rule<Address, String> = { address:Address -> Pass(address).map { it.zipCode }.flatMap(nonEmpty) }
//val checkAddress:Rule<Address, Address> = { address:Address -> Pass(createAddress(J(J(checkNumber(address), checkStreet(address)), checkZip(address)))) }

// Reading form data
fun getField(name:String):Rule<FormData, String> = { form:FormData -> form[name]?.let { Pass(it) } ?: Fail(listOf("field not found")) }

val parseInt:Rule<String, Int> = { s:String ->
    try {
        Pass(s.toInt())
    } catch (nfe:NumberFormatException) {
        Fail(listOf("Not a number"))
    }
}

val readNumber:Rule<FormData, Int> = { formData:FormData -> getField("number")(formData).flatMap(parseInt) }
val readStreet:Rule<FormData, String> = { formData:FormData -> getField("street")(formData) }
val readZip:Rule<FormData, String> = { formData:FormData -> getField("zip")(formData) }

//val readAddress:Rule<FormData, Address> = { formData:FormData -> (createAddress()(formData))
// println("good" + readAddress(Map(("number" -> "29", "street" -> "acacia road", "zip" -> "ABC 123")))
// println("good" + readAddress(Map(("number" -> "-1", "street" -> "", "zip" -> "")))
// }

