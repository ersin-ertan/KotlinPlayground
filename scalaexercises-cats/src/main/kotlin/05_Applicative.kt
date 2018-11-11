import arrow.core.Option
import arrow.data.ListK
import arrow.instances.listk.applicative.applicative
import arrow.instances.option.applicative.applicative
import arrow.instances.option.monad.monad
import arrow.typeclasses.compose

fun applicative() {

    Option.applicative().run { just(1) }.p()
    ListK.applicative().run { just(1) }.p()

    ListK.applicative().compose(Option.applicative()).run { just(1) }.p()

    Option.monad().run { just(1) }.p()
    Option.applicative().run { just(1) }.p()
}