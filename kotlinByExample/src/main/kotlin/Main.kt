// http://kotlinbyexample.org/

class C(val s:String? = null) {
    fun f() {}
}

fun main(args:Array<String>) {
    val c = C("hello")
    println("${c.s?.toUpperCase() ?: "Goodbye"}!")

    val l = listOf(1, 3, 4, 5)

    println("return break")
    for (num in l) {
        println(num)
        if (num == 3) break
    }

    // but how do we break from the for each, without returning to the function
//    l.forEach {
//        println(it)
//        if (it == 3) break;// cant if return, will go to fun main()
//    }

    println("return@foreach") // full list
    l.forEach {
        println(it)
        if (it == 3) return@forEach
    }

    println("return anon func") // full list
    l.forEach(fun(num:Int) {
        println(num)
        if (num == 3) return
    })

    println("theBreak") // 1,3
    run theBreak@ {
        l.forEach {
            println(it)
            if (it == 3) return@theBreak
        }
    }

    println("any") // 1,3 but ambiguous and hard to read
    l.any {
        println(it)
        it == 3
    }

    // iterators
    class Animal(val name:String)

    open class Zoo(val animals:MutableList<Animal>) {

        open operator fun iterator():Iterator<Animal> = animals.iterator()
    }

    val zoo = Zoo(mutableListOf<Animal>(Animal("Tiger"), Animal("Lion")))

    for (animal in zoo) {
        println(animal.name)
    }

    for (j in 1..3 step 2) { // start at 1 and end at 3
        println(j)
    }

    (10 downTo 1 step 3)

    val stringList = l.let {
        // does nothing if it/this is null, else
        val sublist = it.subList(1, l.size)
        sublist.toString()
    }

    // if your object creation takes a lot of chaining to create, use let to working with the value
    // within its scope then discards the variable

    val selectedAnimal = Animal("aa")

    Zoo(mutableListOf(Animal("a"))).iterator().next().let { if (it != selectedAnimal) println("the new selected animal is ${it.name.toUpperCase()}") }

    // functions and classes are final/closed by default


    val map = mapOf("Alice" to 21, "Bob" to 25)
    for ((name, age) in map) { // note the destructured key value in the for
        println("$name is $age years old")
    }

    fun returnTriple() = Triple(1, 2, '3')

    val (omitThirdFrom, compilerHintingUsing, _) = returnTriple()
    println(omitThirdFrom)
    println(compilerHintingUsing)


}

sealed class Mammal(val name:String)

class Cat(val catName:String):Mammal(catName)
class Human(val humanName:String, val job:String):Mammal(humanName)


fun greetMammal(mammal:Mammal):String {
    when (mammal) { // note no else, and casting from Mammal to specific types
        is Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"
        is Cat -> return "Hello ${mammal.name}"
    }
}