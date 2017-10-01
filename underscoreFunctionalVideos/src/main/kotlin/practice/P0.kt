package practice

import kotlin.properties.Delegates

// Understand the differences between using an interface and a sealed class, initialization locality
// class definition locality, field inheritance

// creation of children limited to this file
open class Ad(val adString:String)

class SponsoredAd(adString:String):Ad(adString)

fun Ad.capitalized() = adString.toUpperCase()

private sealed class Visitor {
    open val id:Int by Delegates.notNull<Int>()
    fun giveAd():Ad =
            when (this) {
                is Anon -> Ad("Buy fast, triple triple")
                is User -> Ad("Hey $name, we got this special deal for you")
                is Another -> TODO()
            }

    fun giveAdExternal(anonAd:(Ad) -> Ad, userAd:(Ad) -> Ad):Ad =
            when (this) {
                is Anon -> anonAd(giveAd()) // hmmm?
                is User -> userAd(giveAd())
                is Another -> TODO()
            }

}

private class Anon(override val id:Int):Visitor()
private class User(override val id:Int, val name:String):Visitor()
private class Another:Visitor()

private sealed class Visitorr {
    abstract val idd:Int // delegated property cant be abstract
}

private class Anotherr(override val idd:Int):Visitorr() // force to initialize idd
// requires an instance of Visitor, need not implement id unless it is abstract


// creation of children open for anyone(if wasn't private)
private interface Visitor1 {
    val id:Int
}

private class Anon1(override val id:Int):Visitor1
private class User1(override val id:Int, val name:String):Visitor1

// can also use the get() method for the backing property,
private class Another1():Visitor1 {
    override val id:Int
        get() = 1
}

fun main(args:Array<String>) {

    val visitor1:Visitor = Anon(1)
    val visitor2:Visitor = User(2, "tim")

    val visitor3:Visitor1 = Anon1(3)
    val visitor4:Visitor1 = User1(4, "tom")

    when (visitor1) {
        is Anon -> println(visitor1.id)
        is User -> println(visitor1.name)
    }

    when (visitor4) {
        is Anon1 -> println(visitor4.id)
        is User1 -> println(visitor4.name) // note, name is a type specific variable
    }

    // how do we add functions for both, by adding them to the Visitor class
    listOf<Visitor>(visitor1, visitor2).forEach { println(it.giveAd().adString) }

    // but we want to define the functions externally
    val sponsoredAd = { ad:Ad -> ad as? SponsoredAd ?: SponsoredAd("Sponsered message") }
    listOf<Visitor>(visitor1, visitor2).forEach { println(it.giveAdExternal({ ad -> Ad(ad.capitalized()) }, sponsoredAd).adString) }


    println("Chained ex.")
    listOf(visitor1, visitor2)
            .flatMap { visitor -> listOf(visitor.giveAd()) }
            .map { Ad(it.capitalized()) }
            .fold(mutableMapOf<Int, Ad>(),
                    { acc:MutableMap<Int, Ad>, ad:Ad ->
                        if (ad !is SponsoredAd) acc.put(getPublicAdKey(), ad)
                        else {
                        }
                        // do nothing
                        acc // return

                    }).entries.flatMap { mutableEntry ->
        listOf(buildString {
            append(mutableEntry.key)
            append(":")
            append(mutableEntry.value.adString)
        })

    }.forEach { println(it) }
}

var publicAdKeys = 0
fun getPublicAdKey():Int = publicAdKeys++
