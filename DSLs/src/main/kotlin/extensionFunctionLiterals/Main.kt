package extensionFunctionLiterals

// How to enforce restrictions in your DSL
// http://hadihariri.com/2013/01/21/extension-function-literals-in-kotlin-or-how-to-enforce-restrictions-on-your-dsl/

fun myTopLevelfunction(metadata:String, someFunction:() -> Unit) {
}

fun myTopLevelfunctionButShouldBeNestedWithinFirst(metadata:String, someFunction:() -> Unit) {
}

fun cantControlOrdering() {

    myTopLevelfunctionButShouldBeNestedWithinFirst("should not be parent", {
        myTopLevelfunction("should not be nested", { })
    })
}


infix fun Int.add(input:Int):String = (this + input).toString()


fun weCanDefineExtensionFunctionsAsFunctionLiterals() {

    // TODO: explore the difference
    val subFunc = { fun Int.sub(input:Int):String = (this - input).toString() }
    val multFunc = fun Int.(input:Int):String = (this * input).toString()

    // using this technique we can nest expressions in classes

    class B {
        val b = 1
        fun doB() {}
    }

    class A {
        fun b(s:String, myB:B.() -> Unit) {}
    }

    fun a(s:String, myA:A.() -> Unit) {}

    a("has a's scope") {
        b("has b's scope") {
            doB()
            b
        }
    }
}

fun main(args:Array<String>) {
    val aa = 1 add 2
    println(aa)
}