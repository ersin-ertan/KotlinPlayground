import arrow.core.*
import arrow.typeclasses.binding

fun main(args: Array<String>) {
//    eitherTest()
//    evalTest()
    idTest()

}

fun idTest() {

    // monad and applicative are in the instances-core package

    val id1:Id<String> = Id.just("idJust1")
    val id2:Id<String> = Id.just("idJust2")
    id1.map { "$it!".pl() }.pl()

    id1.flatMap { one -> id2.map { two -> one + two} }.value.pl()

    "-Id Monad Start-".pl()
    val idMonad = Id.monad().binding { id1.bind()+id2.bind() }
    idMonad.pl()
    idMonad.fix().pl()
    idMonad.value().pl()

    Id.applicative().map(id1, id2, {(one, two) -> Pair(one, two)}).value().pl()

}

fun evalTest() {

    fun evalNow():Eval<String> = Eval.now("Now")
    fun evalLater():Eval<String> = Eval.later { Thread.sleep(3000); "Later" }
    fun evalAlways():Eval<String> = Eval.always { Thread.sleep(3000); "Always" }

    fun evalJust():Eval<String> = Eval.just("Just")

    // map and flatmap are always done lazy even for now

    val en = evalNow()
    val el = evalLater()
    val ea = evalAlways()

    en.value().pl()
    en.value().pl()
    el.value().pl()
    el.value().pl()
    ea.value().pl()
    ea.value().pl()

}

fun eitherTest(){

    fun eitherL() = Either.left()
    fun eitherR() = Either.right()
    fun either(b:Boolean):Either<String, Int> = if(b) Left("String") else Right(1)

    either(true).pl()
    either(true).fold({it}, {it}).pl()
    either(false).fold({it}, {it}).pl()
    either(false).map { "map-false1".pl() }
    either(true).map { "map-true1".pl() }
    either(false).mapLeft { "map-false2".pl() }
    either(true).mapLeft { "map-true2".pl() }
    eitherL().pl()
    eitherR().pl()
}


