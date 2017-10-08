package datatypes

import kategory.*

//import kategory.debug.*


fun Any.p() = println(this)

fun main(args:Array<String>) {

    option()

}

private fun option() {

    fun h(s:String) = println("\n=====[$s]=====")
    // kotlin uses ? for null safety, kategory models absence with Option datatyype, a container for the presence
    // of value A
    fun mightReturnSomething(flag:Boolean):Option<String> = if (flag) Option.Some("found") else Option.None
    mightReturnSomething(true).getOrElse { "no value" }.p()
    mightReturnSomething(false).getOrElse { "no value" }.p()

    h("option's some or none")
    val mightRet = mightReturnSomething(true)
    (mightRet is Option.None).p()
    val mr = when (mightRet) {
        is Option.Some -> mightRet.value
        is Option.None -> "Default value"
    }.p()

    // an alternative for pattern matching is Functior/Foldable style operations, an option could be looked at as
    // a collection or foldable structure with either one or zero elements. map allows us to map the inner value
    // to a different type while preserving the option
    h("mapping")
    mightRet.map { it + "!" }.p()
    val wr:Option<String> = Option.None
    wr.map { it + "!" }.p() // wont map if null

    // fold extracts the value from the option, or provides a default if the value is None
    h("folding")
    mightRet.fold({ "def" }, { "val" }).p() // val
    wr.fold({ "def" }, { "val" }).p() // def

    // kategory also adds syntax to all datatypes so you can easily lift them into the context of Option
    h("lifting")
    1.some().p()
    none<String>().p()

    h("option uses")
    h("functor transforms inner contents")
    Option.functor().map(Option(1), { it + 1 }).p() // returns some value=2

    h("applicative computes over independent values")
    Option.applicative().tupled(Option(1), Option("hello"), Option(20.0))

    h("Monad computes over dependent values ignoring absence")
    Option.monad().binding {
        // uses coroutines
        val a = Option(1).bind()
        val b = Option(1 + a).bind()
        val c = Option(1 + b).bind()
        yields(a + b + c)
    }.p()

    Option.monad().binding {
        val a = none<Int>().bind()
        val b = Option(1 + a).bind()
        val c = Option(1 + b).bind()
        yields(a + b + c)
    }.p() // none

//    h("available instances") // not sure what this is
//    showInstances<OptionHK, Unit>()

}
