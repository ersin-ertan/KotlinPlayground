import arrow.Kind
import arrow.core.*
import arrow.data.ListK
import arrow.data.k
import arrow.instances.list.functor.fproduct
import arrow.instances.listk.functor.functor
import arrow.instances.option.functor.functor
import arrow.typeclasses.Functor
import arrow.typeclasses.compose

fun functor() {

    val optionFunctor = object : Functor<ForOption> {
        override fun <A, B> Kind<ForOption, A>.map(f: (A) -> B): Kind<ForOption, B> {
            return map(f)
        }
    }

    Option.functor().run { "hello".some().map { it.length } }.p()
    Option.functor().run { none<String>().map { it.length } }.p()

    val lenOption = Option.functor().lift<String, Int> { it.length }
    lenOption("abcd".some()).p()
    lenOption("hello".some()).p()

    val source = listOf("cats", "is", "cool")
    val product = ListK.functor().run { source.fproduct { it.length } }.toMap()

    product.getOrElse("cats") { 0 }.p()
    product.getOrElse("is") { 0 }.p()
    product.getOrElse("cool") { 0 }.p()

    val listOpt = ListK.functor().compose(Option.functor())
    listOpt.run { listOf<Option<Int>>(1.some(), None, Some(2)).k().map { it.map { it + 1 } } }.p()

}