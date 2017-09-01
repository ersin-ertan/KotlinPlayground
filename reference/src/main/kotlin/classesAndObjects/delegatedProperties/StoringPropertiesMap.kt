package classesAndObjects.delegatedProperties

// storing properties in a map - often in applications that parse json, or do dynamic operations.
// the map instance is a delegate for a delegated property

class User1(val map:Map<String, Any?>) {
    val name:String by map
    val age:Int by map
}

class UserMutable(val map:MutableMap<String, Any?>) {
    var name:String by map
    var age:Int by map
}



fun main(args:Array<String>) {
    val user = User1(mapOf(
            "name" to "Jay",
            "age" to 23
    ))

    println(user.name)
    println(user.age)
    println(user.map)


    val userM = UserMutable(mutableMapOf(
            "name" to "Jay",
            "age" to 23
    ))

    userM.name = "change"
    userM.age = 76

    println(userM.name)
    println(userM.age)
    println(userM.map)
}