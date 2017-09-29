import kotlin.properties.Delegates

fun main(args:Array<String>) {


    class A {

        var name:String by Delegates.notNull()

        // what is the difference from late init
        lateinit var name1:String

        fun printName() {
            //    println(name) // preperty name should be initialized before get
            name = "tim"
            println(name)

//            println(name1) // lateinit property name1 has not been initialized
            name1 = "bill"
            println(name1)
        }
        // Delegates.notNull can be used anywhere and with any type
        // lateinit is faster, but cant be local until kotlin 1.2, can be used with primitives

        val myExpensiveToCreateObject by lazy { // postponed until first usage
            Thread.sleep(1000)
            "hello" // returns a string
        }

    }

    A().printName()


}