import Expression.*
import Expression.Number
import java.math.BigDecimal

// Goal of typeclasses is decoupling

// first we define our Algebraic data type ADT

// asked question about kotlin's smart casting not working for subtypes, thus the repeated calls to expression
// https://stackoverflow.com/questions/46536406/kotlins-smart-casting-not-working-for-subtypes

sealed class Expression {

    class Number(val value:Int):Expression()
    class Plus(val lhs:Expression, val rhs:Expression):Expression()
    class Minus(val lhs:Expression, val rhs:Expression):Expression()
}

object ExpressionEvaluator {

    fun value(expression:Expression):BigDecimal = with(expression) {
        when (this) {
            is Expression.Number -> BigDecimal.valueOf(value.toLong())
            is Expression.Plus -> value(lhs) + value(rhs)
            is Expression.Minus -> value(lhs) - value(rhs)
        }
    }
}

sealed class JsonValue
class JsonObject(val entries:Map<String, JsonValue>):JsonValue()
class JsonArray(val entries:Array<JsonValue>):JsonValue()
class JsonString(val value:String):JsonValue()
class JsonNumber(val value:BigDecimal):JsonValue()
class JsonBoolean(val value:Boolean):JsonValue()
object JsonNull:JsonValue()

object JsonWriter {

    // not that naming the input value and having the value value, you have to explicitly use this.value to get at the value
    // when using with(value), otherwise you get the value:JsonValue from the input
    fun write(_value:JsonValue):String = with(_value) {
        when (this) {
            is JsonObject ->
                "{ " + entries.flatMap { listOf(it.key + ": " + write(it.value)) }.joinToString(", ") + " }"
            is JsonArray -> "[ " + entries.map { write(it) }.joinToString(", ") + " ]"
            is JsonString -> "\"" + value + "\""
            is JsonNumber -> value.toString()
            is JsonBoolean -> value.toString()
            is JsonNull -> "null"
        }
    }

    // fun write(value:JsonConvertible):String = write(value.convertToJson)
//     fun <T>write(value:T, conv:JsonConverter<T>):String = write(conv.convertToJson(value))
}

interface JsonConverter<T> {
    fun convertToJson(value:T):JsonValue
}

private val expressionJsonConverter = object:JsonConverter<Expression> {
    override fun convertToJson(expr:Expression):JsonValue = with(expr) {
        when (this) {
            is Expression.Number -> JsonNumber(BigDecimal.valueOf(value.toLong()))
            is Expression.Plus -> JsonObject(
                    mapOf("op" to JsonString("+"),
                            "lhs" to convertToJson(lhs),
                            "rhs" to convertToJson(rhs)
                    ))
            is Expression.Minus -> JsonObject(
                    mapOf("op" to JsonString("-"),
                            "lhs" to convertToJson(lhs),
                            "rhs" to convertToJson(rhs)
                    ))
        }
    }
}

// any to disallow null
fun <T:Any> JsonWriter.write(value:T, conv:JsonConverter<T>):String = write(conv.convertToJson(value))

fun <T:Expression> JsonWriter.write(value:T):String = write(expressionJsonConverter.convertToJson(value))
// with expression type parameter to explicitly use expressionJsonConverter

// we could use object oriented approach, but we want to keep ADTs clean, as if we couldn't modify Expression
// to inherit from JsonConvertible
// Subtype polymorphism is unsatisfactory, we need ad-hoc polymorphism to eliminate coupling
// we could also use a pramaterized converter taking the value and the converter function, but the minor problem
// is it's more verbose, explicitly passing expressionJosnConverter, it's a standard GoF design pattern,
// but we can use implicits, but kotlin doesn't have it so we revert back to double extension functions


fun main(args:Array<String>) {

    val expr:Expression = Plus(Number(1), Minus(Number(3), Number(2)))
    println(JsonWriter.write(expr, expressionJsonConverter))
    println(JsonWriter.write(expr)) // uses expressionJsonConverter implicitly
}

