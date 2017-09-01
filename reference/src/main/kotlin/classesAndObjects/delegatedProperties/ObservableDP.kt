package classesAndObjects.delegatedProperties

import kotlin.properties.Delegates

// Observable

// Delegates.observable() takes two arguments: initial value and a handle for modifications. The handler gets
// called on every property assignment (after assignment).
// It has three parameters: a property being assigned to, the old value and the new one.

class User {

    var canChange = true

    var name:String by Delegates.observable("no name yet") { property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }

    var email:String by Delegates.vetoable("no email yet") { property, oldValue, newValue ->
        canChange
    }
}


fun main(args:Array<String>) {
    val user = User()
    user.name = "first n"
    user.name = "second n"
    println()

    println(user.email)
    user.email = "first e"
    println(user.email)
    user.email = "second e"
    println(user.email)
    println()

    user.canChange = false
    println(user.email)
    user.email = "first o"
    println(user.email)
    user.email = "second o"
    println(user.email)
    println()

}