package ca.ersin.serialization

import kotlinx.serialization.Optional
import kotlinx.serialization.json.JSON

//@Serializable // this class does not have a constructor Cannot access 'Serializable': it is internal in 'kotlin.io'
data class Data(val a: Int, @Optional val b: String = "42")

fun main(args: Array<String>) {
//    println(JSON.stringify(Data(42))) // {"a": 42, "b": "42"}
//    val obj = JSON.parse<Data>("""{"a":42}""") // ca.ersin.serialization.Data(a=42, b="42")
//    println(obj)
}