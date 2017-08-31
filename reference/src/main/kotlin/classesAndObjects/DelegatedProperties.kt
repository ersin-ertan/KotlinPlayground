package classesAndObjects

import kotlin.reflect.KProperty

// delegated properties - nice to have properties
// lazy properties: value gets computed only upon first access
// observable properties: listeners get notified about change to this properties
// storing properties in ao map, instead of a separate field for each property

// enter delegated properties

class Example {
    var p:String by Delegate()
    // get and set corresponding to the property will be delegated to its getValue() set value methods.
    // Property delegates don't have to implement any interface, but have to provide a getValue function
    // and a set for vars
    val pp:String by Delegate1()
}


class Delegate {
    operator fun getValue(thisRef:Any?, property:KProperty<*>):String {
        return "$thisRef, delegates '${property.name}' to me"
    }

    operator fun setValue(thisRef:Any?, property:KProperty<*>, value:String) {
        println("$value has been assigned to '${property.name}' in $thisRef")
    }
}

class Delegate1 {
    operator fun getValue(thisRef:Any?, property:KProperty<*>):String {
        return "$thisRef, delegates '${property.name}' to me"
    }
}

fun main(args:Array<String>) {

    val e = Example()
    println(e.p)
    e.p = "test"
    println(e.pp)

}


// p delegates to an instance of Delegate, the getValue function from Delegate is called, the first param is the object
// we read p from and the second hold a description of p itself

// when p, setValue is called, the fist two parameters are the same, and the third hold the value being assigned

