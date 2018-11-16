import arrow.core.Eval
import arrow.core.None
import arrow.core.Option
import arrow.data.ForListK
import arrow.data.ListK
import arrow.data.k
import arrow.instances.extensions
import arrow.instances.list.foldable.*
import arrow.instances.listk.foldable.exists
import arrow.instances.listk.monoid.monoid
import arrow.instances.monoid
import arrow.instances.option.monoid.monoid
import arrow.instances.option.monoidK.monoidK
import arrow.instances.semigroup

fun foldable() {


    listOf(1, 3, 5).foldLeft(0) { acc: Int, next: Int -> acc + next }.p()

    ForListK extensions {
        listOf(1, 3, 5).k().foldLeft<Int, Int>(1, { a, n -> a + n }).p()
    }


    listOf("a", "b").k().foldLeft("Appended String:") { a: String, n: String -> a.plus(n) }.p()

    val lazyRequest = listOf(1, 2, 3).k().foldRight(Eval.now(0)) { i: Int, acc -> Eval.later { i + acc.value() } }
    lazyRequest.value().p()

    listOf("a", "b").fold<String>(String.monoid()).p()
    listOf(1, 3, 5).fold<Int>(Int.monoid()).p()

    listOf("a", "bb").k().foldMap(Int.monoid()) { s -> s.length }.p()
    listOf(1, 2, 3).k().foldMap(String.monoid()) { i -> i.toString() + "-" }.p()

    val ks = listOf(listOf(1).k(), listOf(2, 4).k())
    val k = listOf(listOf(1), listOf(2)).k()

    ks.fold(ListK.monoid()).p()
    listOf(Option(1), None, Option(2)).fold(Option.monoid(Int.semigroup())).p()
    listOf(Option("one"), None, Option("two")).fold(Option.monoid(String.semigroup())).p()

    val ll = listOf(Option(1))
    val m = Option.monoidK().algebra<Int>()
//    ll.fold(m) // see issue https://github.com/arrow-kt/arrow/issues/1126

    // not sure how to use monoidK

    val l = listOf(1, 3, 5)

    listOf(1, 3, 5).find { it > 4 }.p()
    listOf(1, 3, 5).find { it > 6 }.p()

    l.k().exists { i -> i == 1 }.p()
    l.k().exists { i -> i == 8 }.p()
    l.exists { i -> i == 1 }.p()

    l.k().forAll { i -> i < 6 }.p()
    l.k().forAll { i -> i < 2 }.p()
    l.forAll { i -> i < 6 }.p()

    l.k().toList().p()
    Option(43).toList().p()
    None.toList().p()

    l.k().filter { it < 4 }.p() // just stdlib

//    fun parseInt(s:String):Option<Int> = Either.runCatching { s.toInt() }

}