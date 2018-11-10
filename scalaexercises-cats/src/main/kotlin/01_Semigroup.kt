import arrow.core.Function1
import arrow.core.Option
import arrow.core.some
import arrow.data.ListK
import arrow.data.MapK
import arrow.data.k
import arrow.instances.MapKSemigroupInstance
import arrow.instances.list.semigroup.maybeCombine
import arrow.instances.listk.semigroup.semigroup
import arrow.instances.mapk.semigroup.semigroup
import arrow.instances.option.semigroup.semigroup
import arrow.instances.semigroup

fun semigroup() { // combine

    Int.semigroup().run { 1.combine(2).p() }.p()
    ListK.semigroup<Int>().run { listOf(1, 2).maybeCombine(listOf(3, 4)) }.p()
    Option.semigroup(Int.semigroup()).run { 1.some().combine(2.some()) }.p()
    Option.semigroup(Int.semigroup()).run { 1.some().combine(Option.empty()) }.p()
    Function1<Int, Int> { it + 1 }.compose(Function1<Int, Int> { it * 10 })
    // todo find semigroup version, because compose is sequentially taking 6+1 then 7*10

    val aMap = mapOf("foo" to mapOf("bar" to 5).k()).k()
    val bMap = mapOf("foo" to mapOf("bar" to 6).k()).k()
    val s: MapKSemigroupInstance<String, Int> = MapK.semigroup(Int.semigroup())
    val sg: MapKSemigroupInstance<String, MapK<String, Int>> = MapK.semigroup(s)
    val combinedMap = MapK.semigroup<String, MapK<String, Int>>(MapK.semigroup(Int.semigroup())).run {
        val m = aMap.combine(bMap)
        ("keys " + m.keys.size).p()
        m.keys.p()
        ("values" + m.values.size).p()
        "-----keys".p()
        m.values.forEach {
            ("-----" + it.keys.toString()).p()
            "-----values".p()
            ("-----" + it.values.toString()).p()
        }
        m.p()
        m
    }.get("foo").p()
}