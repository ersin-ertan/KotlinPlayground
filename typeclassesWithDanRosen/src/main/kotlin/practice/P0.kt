package practice


sealed class Expression
class Number(val value:Int):Expression()
class Plus(val lhs:Expression, val rhs:Expression):Expression()
class Minus(val lhs:Expression, val rhs:Expression):Expression()

sealed class JsonValue
class JsonObject(val entries:Map<String, JsonValue>):JsonValue()
class JsonArray(val entries:Array<JsonValue>):JsonValue()
class JsonString(val value:String):JsonValue()
class JsonNumber(val value:Int):JsonValue() // int for simplification was big decimal
class JsonBoolean(val value:Boolean):JsonValue()
object JsonNull:JsonValue()

object JsonWriter {

    fun write(jsonValue:JsonValue):String = with(jsonValue) {
        when (this) {
            is JsonObject -> buildString {
                // consider buildString when inside loops, or will be called within loops
                append("{ ")
                append(entries.flatMap { listOf(it.key + ": " + write(it.value)) }.joinToString(", "))
                append(" }")
            }
            is JsonArray -> "[ " + entries.joinToString(", ") { write(it) } + " ]"
            is JsonString -> "\"" + value + "\""
            is JsonNumber -> value.toString()
            is JsonBoolean -> value.toString()
            is JsonNull -> "null"
        }
    }
}

// instead of using the JsonConvertible and making all JsonValue implement it, we decouple our data and converter thus

interface JsonConverter<T> {
    fun convertToJson(value:T):JsonValue
}

private val expressionJsonConverter = object:JsonConverter<Expression> {
    override fun convertToJson(expr:Expression):JsonValue = with(expr) {
        when (this) {
            is Number -> JsonNumber(value)
            is Plus -> JsonObject(
                    mapOf("op" to JsonString("+"),
                            "lhs" to convertToJson(lhs),
                            "rhs" to convertToJson(rhs)
                    ))
            is Minus -> JsonObject(
                    mapOf("op" to JsonString("-"),
                            "lhs" to convertToJson(lhs),
                            "rhs" to convertToJson(rhs)
                    ))
        }
    }
}

fun <T:Any> JsonWriter.write(value:T, conv:JsonConverter<T>):String = write(conv.convertToJson(value))

// this is helping the compiler, and pefer this way instead of the delegating all calls with a conv to the <Any> write()
fun <T:Expression> JsonWriter.write(value:T, conv:JsonConverter<Expression> = expressionJsonConverter):String = write(conv.convertToJson(value))

fun main(args:Array<String>) {

    val expr:Expression = Plus(Number(1), Minus(Number(3), Number(2)))
    println(JsonWriter.write(expr))

    // what is the importance of having two extensions for JsonWriter, one with the Any and the other with Expression
    // type parameter
}