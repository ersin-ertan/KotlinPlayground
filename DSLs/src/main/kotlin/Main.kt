// Creating Small DSLs with Idiomatic Kotlin Talk at Jetbrains Night Seoul 2016
// https://www.youtube.com/watch?v=-aF4kfrMBKs

// TODO(stopped at 23:56 - talking about extension function and dsl specific nesting)
// re approach when you finish DLS in Kotlin in Action

// https://www.youtube.com/watch?v=GjGQpSFieXA
// is the 2017 talk, a slight bit more detail, watch it too

import java.io.Closeable

fun higherOrder(x:Int, y:Int, func:(Int, Int) -> Int):Int{
    return func(x,y)
}
fun higherOrderSingle(x:Int, func:(Int) -> Int):Int{
    return func(x)
}

fun intIntAdd(a:Int, b:Int )= a+b
fun intIntSub(a:Int, b:Int )= a-b


fun add(a:Int, b:Int=2, c:Int = 3) = a+b+c

// unit is a singleton pattern

fun hell() = "hell"

fun multTwo(i: Int): Int = i*2

fun main(args : Array<String>) {
//    val mes = "hel"
//    println(mes)
//    add(1)
//    add(1, c = 4)
    println(higherOrder(1,2, ::intIntAdd))
    println(higherOrder(1,2, ::intIntSub))
    println(higherOrderSingle(3, ::multTwo))

    println(higherOrderSingle(2, {x -> x*2})) // passing in the lambda
    println(higherOrderSingle(3,{it*2})) // if single param, use it

    println(higherOrderSingle(4){
        it + 1
    }) // if the function is the last param, but why

    using(MyObj){
        // do work
    }
}

object MyObj : Closeable {
    override fun close() {
        // nothing
    }

}

fun using(obj:Closeable, block:() -> Unit){
    try{
        block()
    }finally {
        obj.close()
    }
}