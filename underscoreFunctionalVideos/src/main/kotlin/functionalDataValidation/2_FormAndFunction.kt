package functionalDataValidation

// Form and Function - in which we build sequences of rules

data class Address2(val number:Int, val street:String) {
    companion object {
        val apply = { num:Int, st:String -> Address2(num, st) }
    }
}

// transform our view to our class in one go

// we have the form data, we have our get and parse int rules, and for the street

// how do we sequence the get and parse int methods into one step readNumber


sealed class Result2<A> {

    fun <B, C> and(that:Result2<B>, fn:(A, B) -> C):Result2<*> { // minor adjustments from scala, had to use *, because
        // required is Result2<C> but fail is not...?, and the else if case is not working
        return if (this is Pass2<A> && that is Pass2<B>) Pass2(fn(this.value, that.value)) // no way to combine a and b, pass in as second param
        else if (this is Fail2 && that is Pass2<B>) Fail2(this.msg)
        else if (this is Pass2<A> && that is Fail2) Fail2(that.msg)
        else/* (this is Fail2 && that is Fail2)*/ Fail2((this as Fail2).msg + (that as Fail2).msg)
    }

    // a general combinator for stringing two rules together
    fun <B> flatMap(r:(A) -> Result2<B>):Result2<B> = // implement the pattern matching here
            when (this) {
                is Pass2 -> r(this.value)
                is Fail2 -> TODO()
            }
}

class Pass2<A>(val value:A):Result2<A>()
class Fail2(val msg:List<String>):Result2<Nothing>() // Nothing or Unit

private fun <A> rule(f:(A) -> Result2<A>) {}
private typealias Rule2<A, B> = (A) -> Result2<B>

class FormData {
    fun getField(name:String):Result2<String> = when (name) {
        "number" -> Pass2<String>("1")
        "street" -> Pass2("23 King St")
        else -> TODO()
    }
}

// we need a mechanism of passing a result from rule to rule, thus we refactor pass and fail
//val readNumber:Rule2<FormData, Int> = { form:FormData ->
//    val resultA = form.getField("number") // (form)
//    val resultB:Result2<Int> = when (resultA) {
//        is Pass2 -> Pass2(Integer.parseInt(resultA.value))
//        is Fail2 -> TODO()
//    }
//    resultB
//}


// if we have subsequent rules, we have to write pattern matching, variable definitions,
// to factor this out, we use flatmap to do the pattern matching for me

val readNumber:Rule2<FormData, Int> = { form:FormData ->
    form.getField("number").flatMap { Pass2(Integer.parseInt(it)) }
}
// but flatmap throws away errors, thats fine in this case, in order to check an integer you need a string, and will preserve errors
// this is a monad

// our final read address rule after the modifications
val readAddress:Rule2<FormData, Address2> = { form:FormData ->
    val numberResult = readNumber(form)
    val streetResult = form.getField("street")

    @Suppress("UNCHECKED_CAST")
    numberResult.and(streetResult, Address2.apply) as Result2<Address2>
}

// summary
// rules are now pipelines (that can alter data as it passes through)
// results carry value from rule to rule
// we sequence with map and flatMap(and optionally for comprehensions)
// sequencing throws away errors(but composition using and retains them)