package DSLsWithIdiomaticKotlin

//class DSLsWithIdiomaticKotlin.Person
open class Person

data class Customer(val id:Int, var name:String, val email:String):Person()

// type is final by default cannot be inherited from
//data class DSLsWithIdiomaticKotlin.Customer(val id:Int, var name:String, val email:String):DSLsWithIdiomaticKotlin.Person

fun main(args: Array<String>) {
    val customer = Customer(1, "joe", "joe@email.com")

    println(customer) // if class will print DSLsWithIdiomaticKotlin.Customer@4tec443
    // but if you add data class, then will print out all of the properties
}