/*
package practice

data class ValidateMe(val i:Int, val s:String)

sealed class Result<A> {

    fun <B> flatMap(rule:Rule<A, B>):Result<B> = when (this) {
        is Pass -> rule(this.value)
        is Fail -> TODO() // Fail(this.errorMsgList.plus("flatmapFail"))
    }

    fun <B, C> and(that:Result<B>, func:(A, B) -> C):Result<C> = with(this) {
        when {
            this is Pass<A> && that is Pass<B> -> Pass(func(this.value, that.value))
            this is Pass<A> && that is Fail -> Fail(that.errorMsgList.plus("and fail"))
            this is Fail && that is Pass<B> -> Fail(this.errorMsgList.plus("and fail"))
            this is Fail && that is Fail -> Fail(this.errorMsgList.plus("and fail").plus(that.errorMsgList.plus("and fail")))
            else -> TODO()
        }
    }
}

class Pass<A>(val value:A):Result<A>()
class Fail(val errorMsgList:List<String>):Result<>() // what is the return value

typealias Rule<A, B> = (A) -> Result<B>
fun <A> rule(func:(A) -> Result<A>) {} // isn't this just a map?

class Form {

    fun getField(name:String):Result<String> = when (name) {
        "int" -> Pass("1")
        "string" -> Pass("myString")
        else -> TODO()
    }
}

val readInt:Rule<Form, Int> = { it.getField("int").flatMap { Pass<Int>(Integer.parseInt(it)) } }
val readString:Rule<Form, String> = { it.getField("string").flatMap { Pass<String>(it) } }

val validate:Rule<Form, ValidateMe> = { form:Form -> readInt(form).and(readString(form), { i:Int, s:String -> ValidateMe(i, s) }) }

fun doValidation() {

    val response = validate(Form())
//    response.flatMap { validateMe: ValidateMe -> println(validateMe) }
}*/
