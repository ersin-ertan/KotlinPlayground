// the key's correspond with the variable name, uses reflection KProperty to get the classes field names

class MapDelegate(map:Map<String, Any>) {
    val a:String by map
    val b:Boolean by map
    val c:Int by map
}

class MD(map:Map<Any, Any>) { // using asking for keys that don't exist as fields
    val a:String by map
    val b:Boolean by map
}

// using mutable map to modify values in two ways, as individual values via put and referencing the field directly
class MMD(var map:MutableMap<String, Any>) {
    var a:Int by map // note 'a' is a var
}

fun main(args:Array<String>) {

    val a = MapDelegate(mapOf("a" to "String of a", "b" to true, "c" to 99))
    println(a.a)
    println(a.b)
    println(a.c)

    val b = MD(mapOf(1 to "a", "b" to Unit))
//    println(b.a) // key a is missing in the map
//    println(b.b) // Unit cannot be cast to boolean

    val c = MMD(mutableMapOf("a" to 1))
    println(c.a)

    c.map.put("a", 2)
    println(c.a)

    c.a = 3
    println(c.a)

    c.map = mutableMapOf("a" to 4) // note changing the backing map does not change variable 'a's internal reference
    // of the value
    println(c.a) // will have different values than c.map
    println(c.map)

}