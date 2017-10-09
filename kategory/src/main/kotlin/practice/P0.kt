package practice

import datatypes.p
import kategory.Option
import kategory.Validated
import kategory.invalid
import kategory.valid

data class User(val name:String, val age:Int)

// creating the model of validation might return conflicts sequentially

abstract class ValidationReader<A> {

    abstract fun read(value:String):Option<A>

    companion object {

        val readName:ValidationReader<String> = object:ValidationReader<String>() {
            override fun read(name:String):Option<String> = Option(name)
        }

        val readAge:ValidationReader<Int> = object:ValidationReader<Int>() {
            override fun read(age:String):Option<Int> = if (age.toInt() >= 18) Option(age.toInt()) else Option.None
        }
    }
}

sealed class UserEntranceError {
    data class MissingEntranceValue(val value:String):UserEntranceError()
    data class ParseError(val value:String):UserEntranceError()
}

data class FreeUserValidation(val map:Map<String, String>) {

    fun <A> parse(validationReader:ValidationReader<A>, key:String):Validated<UserEntranceError, A> =
            with(Option.fromNullable(map[key])) {
                when (this) {
                    is Option.Some -> {
                        val s = validationReader.read(value)
                        when (s) {
                            is Option.Some -> s.value.valid()
                            is Option.None -> UserEntranceError.ParseError(key).invalid()
                        }
                    }
                    is Option.None -> Validated.Invalid(UserEntranceError.MissingEntranceValue(key))
                }
            }
}

fun main(args:Array<String>) {

}