import arrow.core.None
import arrow.core.Option
import arrow.core.some
import arrow.data.ForListK
import arrow.data.ListK
import arrow.instances.extensions

fun apply() {

    val intToString: (Int) -> String = { it.toString() }
    val double: (Int) -> Int = { it * 2 }
    val addTwo: (Int) -> Int = { it + 2 }

    Option.run { 4.some().map(intToString) }.p()
    Option.run { 4.some().map(addTwo) }.p()
    Option.run { 4.some().map(double) }.p()
    Option.run { None.map(addTwo) }.p()

//    val listOpt = ListK.apply().
//    val plusOne = { x: Int -> x + 1 }

    ForListK extensions {

    }

    ListK.just(listOf(2)).run {

    }




}