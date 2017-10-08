package datatypes

import kategory.*

// Validated allows you to have all these errors reported simultaneously, all of the things wrong with the username as well
// as the problems with the password. Or a misspelled or missing field in a config to be validated separately from another

// Common problems:
// filling out a form, error on input, change resubmit, and do this back and forth which frustrating
// reading config file, if configuration lib retuns Try or Either parsing looks like

data class ConnectionParams(val url:String, val port:Int)

fun <A> config(key:String):Either<String, A> = TODO()

/*
private val c = config<String>("url")
        .flatMap { url ->
            config<Int>("port").map { ConnectionParams(url, it) }
        }
*/

// run program, key url is not found, keye was endpoint, change code rerun now port key was not well form integer


// Parallel Validation - lets look at cofig parsing, which is represented by Map<String, String>, parsing handled by
// Read type class, to which we provide instances only for String and Int for brevity

abstract class Read<A> {

    abstract fun read(s:String):Option<A>

    companion object {
        val stringRead:Read<String> = object:Read<String>() {
            override fun read(s:String):Option<String> = Option(s)
        }
        val intRead:Read<Int> = object:Read<Int>() {
            override fun read(s:String):Option<Int> =
                    if (s.matches(Regex("-?[0-9]+"))) Option(s.toInt()) else Option.None
        }
    }
}

// enumerate our errors - when asking for a config value, one of two things can go wrong, missing field, or expected
// type is not well formed

sealed class ConfigError {
    data class MissingConfig(val filed:String):ConfigError()
    data class ParseConfig(val field:String):ConfigError()
}

// we need a data type that can represent either a successful value(a parsed config), or an error.

/*
@higherkind sealed class Validated<out E, out A> : ValidatedKind<E, A> {

    data class Valid<out A>(val a: A) : Validated<Nothing, A>()

    data class Invalid<out E>(val e: E) : Validated<E, Nothing>()

}*/

// lets write the parser

data class Config(val map:Map<String, String>) {

    fun <A> parse(read:Read<A>, key:String):Validated<ConfigError, A> {

        val v = Option.fromNullable(map[key])
        return when (v) {
            is Option.Some -> {
                val s = read.read(v.value)
                when (s) {
                    is Option.Some -> s.value.valid()
                    is Option.None -> ConfigError.ParseConfig(key).invalid()
                }
            }
            is Option.None -> Validated.Invalid(ConfigError.MissingConfig(key))
        }
    }
}


// now we can write the parallel validator, recall that we can only do parallel validation if each piece in independent.
// how can we enforce data independence, by asking for all args up front.

fun <E, A, B, C> parallelValidate(v1:Validated<E, A>, v2:Validated<E, B>, f:(A, B) -> C):Validated<E, C> = when {
    v1 is Validated.Valid && v2 is Validated.Valid -> Validated.Valid(f(v1.a, v2.a))
    v1 is Validated.Valid && v2 is Validated.Invalid -> v2
    v1 is Validated.Invalid && v2 is Validated.Valid -> v1
    v1 is Validated.Invalid && v2 is Validated.Invalid -> TODO()
    else -> TODO()
}

// in the case where we have both errors, we should report both, there is no way to combine config errors, as clients
// we can change our validated values where the error can be combined, like a list<ConfigError>, We use a
// NonEmptyList<ConfigError>, which statically guarantees that we have at least one value, which aligns with the fact
// that if we have an Invalid, then we most certainly have at least one error. This is a common method on Validated thus
// we have toValidateNel that turns any Validated<E,A> value to a Validated<NonEmptyList<E>, A> The type alias
// ValidatedNel<E,A> is provided

// lets try again
fun <E, A, B, C> parallelValidate1(v1:Validated<E, A>, v2:Validated<E, B>, f:(A, B) -> C):Validated<NonEmptyList<E>, C> = when {
    v1 is Validated.Valid && v2 is Validated.Valid -> Validated.Valid(f(v1.a, v2.a))
    v1 is Validated.Valid && v2 is Validated.Invalid -> v2.toValidatedNel()
    v1 is Validated.Invalid && v2 is Validated.Valid -> v1.toValidatedNel()
    v1 is Validated.Invalid && v2 is Validated.Invalid -> Validated.Invalid(NonEmptyList(v1.e, listOf(v2.e)))
    else -> throw IllegalStateException("Not possible value")
}

// must add else, even though we covered all possible states

// When no errors are present in the configuration, we get a ConnectionParams wrapped in a Valid instance

val config = Config(mapOf("url" to "123.0.0.1", "port" to "1223"))

// what happens when you have one or more errors - they accumulate in a NonEmptyList wrapped in an Invalid Instance
val badConfig = Config(mapOf("url" to "123.0.0.1", "port" to "im a string"))
// //Invalid(e=NonEmptyList(all=[ParseConfig(field=port)]))

val valid = parallelValidate1(
        config.parse(Read.stringRead, "url"),
        config.parse(Read.intRead, "port"),
        ::ConnectionParams)
// ConnectionParams takes in the url, port -> ConnectionParams(url, port)
// returns Valid(a=ConnectionParams(url=123.0.0.1, port=1223)

val invalid = parallelValidate1(
        badConfig.parse(Read.stringRead, "url"),
        badConfig.parse(Read.intRead, "port"),
        ::ConnectionParams)


// Sequential Validation  - if you want to accumulate errors but occasionally run into places where sequential validation
// is required, use Validated withEither function to allow you to temporarily turn a Validated instance into an Either
// instance and apply it to a function

fun positive(field:String, i:Int):Either<ConfigError, Int> = if (i >= 0) i.right() else ConfigError.ParseConfig(field).left()

val config1 = Config(mapOf("houseNum" to "-42"))

val houseNumber = config1.parse(Read.intRead, "houseNum").withEither { either -> either.flatMap { positive("housNum", it) } }
// Invalid(...)

// don't know what this is
/*
import kategory.debug.*

showInstances<ValidatedKindPartial<String>, Unit>()
//[Applicative, ApplicativeError, Foldable, Functor, SemigroupK, Traverse, TraverseFilter]
*/

fun main(args:Array<String>) {
    println(valid)
    println(invalid)
}