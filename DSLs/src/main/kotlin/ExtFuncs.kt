import java.math.BigDecimal

// to extend class without an inheritance chain

fun String.hello(){
    println("hello")
}

infix fun String.shouldEqual(value:String) = this == value
// add infix, any member or extension function that takes a single pram can
// modify it with infix, to call via infix notation

fun main(args: Array<String>) {
    "Hadi ".hello()

    "hello".shouldEqual("hello")

    "hello" shouldEqual "hello"

    val bigDec = BigDecimal(1) // get rid of this noise

    val bd = 1.bd

    val longer = 100L
}

private val Int.bd: BigDecimal
    get() {
        return BigDecimal(this)
    }
