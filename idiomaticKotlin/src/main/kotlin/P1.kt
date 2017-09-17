interface Computable {
    fun compute(int:Int) = 0 // default implementation
}

object DefaultComputer:Computable

object CustomComputer:Computable {

    override fun compute(int:Int):Int {
        return 1
    }

    fun printNull(yes:Boolean):Boolean? = if (yes == true) null else false

}

data class Func(val a:Int, var multiplier:Int = 2) {
    val initVal = a * multiplier
}

fun CustomComputer.nullCompute(int:Int):Int? = null


fun main(args:Array<String>) {


    val f = Func(5)

    CustomComputer.nullCompute(1)?.let { result ->
        // will do if not null

        val a = result + f.initVal
    }

    CustomComputer.compute(2).let { result ->
        // will last for the duration of the let

        val resOfWhen = when (result) {
            0 + 1 -> Func(0)
            4 -> Func(1)
            is Int -> Func(2)
            else -> Func(3)
        }.apply {
            multiplier = 1
        }

        println(resOfWhen)

        val firstIsTrue = CustomComputer.printNull(true)?.and(false) ?: true
        // the maybe is null thus result is true

        val secondIsFalse = CustomComputer.printNull(false)?.and(true).let { null } ?: false ?: true
        // let returns null, which is checked by the first ?: and returns false

        val thirdIsTrue = CustomComputer.printNull(false)?.and(true).let { null } ?: null ?: true
        // let returns null, which is checked by the first ?: which is false, and is checked by the second ?: which returns true

        "First and second nulls".p()
        firstIsTrue.p()
        secondIsFalse.p()
        thirdIsTrue.p()

    }

    val shoppingMap =
            mapOf("shopper" to "tom",
                    "shopping list" to listOf("apple", "carrot"),
                    "cart" to mapOf("apple" to 988776345L, "cookie" to 987234987L))

    shoppingMap.p()

}