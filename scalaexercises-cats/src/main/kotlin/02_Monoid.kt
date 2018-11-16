import arrow.data.MapK
import arrow.data.k
import arrow.instances.list.foldable.foldMap
import arrow.instances.mapk.monoid.monoid
import arrow.instances.monoid
import java.lang.Integer.toString

fun monoid() {


    String.monoid().run { empty() }
    String.monoid().run { combineAll(listOf("a", "b")) }.p()
    String.monoid().combineAll(listOf()).p()

    MapK.monoid<String, Int>(Int.monoid()).run { mapOf("a" to 1, "b" to 2).k().combine(mapOf("a" to 3).k()) }.p()
    MapK.monoid<String, Int>(Int.monoid()).run { mapOf<String, Int>() }.p()

    val l = (1..5).toList().k()
//    l.foldMap()
    l.foldMap(String.monoid()) { it.toString() }.p()

//    l.foldMap(Tuple2.monoid(Int.monoid(), String.monoid()), {it.toString()}) // todo why we take in two params for the fold map tuple monoid

    // ---

    "---".p()

    MapK.monoid<String, Int>(Int.monoid()).combineAll(
            listOf(
                    mapOf("a" to 1, "b" to 2).k(), mapOf("a" to 3).k()
            )
    ).map.p()

    MapK.monoid<String, Int>(Int.monoid()).combineAll(listOf()).map.p()


    listOf(1, 3, 5).foldMap(Int.monoid()) { it }.p()
    listOf(1, 3, 5).foldMap(String.monoid(), ::toString).p()

}

typealias T<I, J> = Pair<I, J>