package practice

import gettingStarted.p

// apply calls the block, with this value as its receiver and returns this value
fun listPreinit():List<Int> = List<Int>(3, { int -> 1 })  // this version is preinitialized with three 1s

fun main(args:Array<String>) {
    listPreinit().p()
    for ((index, value) in listPreinit().withIndex()) {
        println("Index:$index Value:$value")
    }
    var a:Int = 0
    val v = 1.apply { a = (this + 1) * 2 } // like an computation block
    v.p()
    a.p()

    val mutableListPreinit = MutableList<Int>(1, { 0 }).apply {
        println("Initing $this")
        add(1)
    }
    mutableListPreinit.p()

    // or

    val mutableListPreinit1 = mutableListOf<Int>(1, 3).apply {
        println("Initing $this")
        add(1)
    }
    mutableListPreinit1.p()

    var b:Int? = 0
    val c = b?.toString() ?: "null"
    b = null
    val d = b?.toString() ?: "null"

    var bool:Boolean? = null
    if (bool == true) println(true) else println(bool)
    bool = false
    if (bool == true) println(true) else println(bool)
    // else statement does both null and false

    // for i in 1..10 is a closed range, which includes the max
    // for i in 1 until 10 is a half open range, which excludes the max
    // down two which is which is half open

    val map = mapOf('a' to 1, 'b' to 2)
    for ((k, v) in map) {
        println("key:$k Value:$v")
    }

    for (k in 'a'..'b') println(map[k])
}