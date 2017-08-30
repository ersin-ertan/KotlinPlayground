package practice

import gettingStarted.p

// practicing idioms

fun main(args:Array<String>) {
    a
    b
    maps()
    val c = a?.toString() ?: "a is null" // don't forget this one, null check, else
    // if the value is not null, use the value, ?: else the alternative

    println("gettingStarted.getList")
    val list = mutableListOf<Int>().apply {
        // apply is like 'with' but on a specific variable, thus there is no need to pass the variable in
        add(1)
        add(2)
        toString().p()
        plus(3).p()
    }

    println("d and practice.getE")
    d.p()
    e.p()
}


val a = null?.let { println("is null") } // wont print because is null - not null checker
val b = true?.let { println("not null") } // will print because is not null - not null checker
// if the value is not null ? do the let statement, odd because null is not explicitly covered

fun maps() {
    val map = mapOf(1 to "a", 2 to "b")
    for ((k, v) in map) {
        v.p()
    }
    map.values
    map.keys

    print("closed range ")
    for (i in 1..map.size) println(map[i]) // closed range - size inclusive
    print("half open range ")
    for (i in 1 until map.size) println(map[i]) // half open range - size exclusive
    print("downTo 1 ")
    for (i in map.size downTo 1) println(map[i]) // half open range - size exclusive

    map.forEach { it.p() } // it is of type gettingStarted.getMap.entry
}

val d = true?.toString() ?: "null"
val e = null?.toString() ?: "this is null"


val nul:Boolean? = null
val f = if (nul == true) true else false // else is the false or null case
val g = nul?.equals(true) ?: false // else is the false or null case // both are the same

val h = null?.toString() ?: "else null"
val i = 2?.toString() ?: "else null"
