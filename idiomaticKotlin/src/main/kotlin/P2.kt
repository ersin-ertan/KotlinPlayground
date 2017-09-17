import C.b
import C.bb

interface B {
    fun b():Unit?
}

object C:B {
    val a = 1
    var bb = 3
    override fun b() = Unit
}

object E:B {
    override fun b():Unit? = null
}

fun main(args:Array<String>) {
    C.b()
    C.let { bb = C.a }
    var c = C.a.also { bb * it } // returns 1
    println(C.bb)
    val d = C.bb.apply { 9 } // returns 1
    println(C.bb)
    val e = C.bb.let { 10 } // returns 10 to e
    println(C.bb)
    val f = C.a.also { bb = bb * it } // returns 1
    println(C.bb)
//    val g = C.a.also { b *= it } // returns 1 // will yield an Error: Kotiln: [Internal Error] , but above won't

    println()
    println(c)
    println(d)
    println(e) // let is doing the assignment
    println(f)

    C.b().let { } // ? not needed because the type is known
    E.b()?.let { } // is needed because the type is/can be null


    println(E.b())

    fun E.b() = 1
    println(E.b()) // cannot do this, will simply return null, from the original object

    mapOf("name" to "tim", "family" to listOf("tom", "tina")).p()

    println(E.b()?.p() ?: "was null")

    data class AA(val a:Int = 1)
    AA().p()
    AA(2).p()

    val aa = when (AA().a) {
        1 -> 1
        else -> try {
            2
        } catch (e:Exception) {
            3
        } finally {
            "aa caught".p()
        }
    }
    aa.p()
    println()

    val ab = when (AA(2).a) {
        1 -> 1
        else -> try {
            2
        } catch (e:Exception) {
            3
        } finally {
            "aa caught".p()
        }
    }
    ab.p()
    println()

    val ac = when (AA(0).a) {
        1 -> 1
        else -> try {
            2
            throw Exception()
        } catch (e:Exception) {
            3
        } finally {
            "aa caught".p()
        }
    }
    ac.p()


    data class AB(var a:Int = 0, var b:Int = 0, var cc:Int = 0)
    AB().apply {
        a = 1
        b = 1
        cc = 1
    }
}