import arrow.core.ForOption
import arrow.core.None
import arrow.core.Option
import arrow.data.*
import arrow.instances.ForOptionT
import arrow.instances.extensions
import arrow.instances.list.monad.ifM
import arrow.instances.listk.applicative.applicative
import arrow.instances.listk.monad.monad
import arrow.instances.option.monad.flatten
import arrow.instances.option.monad.monad
import arrow.typeclasses.binding

fun monad() {

    // adds flatten - takes value in nested context F<F<A>> and joints contexts together to have a single context

    Option.monad().binding { Option(1) }.flatten().p()

    ForOption extensions { Option(Option(1)).flatten().p() }
    Option.monad().binding { None }.flatten().p()

    ForListK extensions {
        listOf(listOf(1, 2), listOf(1, 2)).flatten().p()
    }

    Option.monad().just(42).p()

    ForListK extensions {
        listOf(1, 2, 3).flatMap { listOf(it, it) }.p()
    }

    ForOption extensions {
        Option(true).ifM({ Option("t") }, { Option("f") }).p()
    }

    ForListK extensions {
        listOf(true, false, true).ifM({ listOf(1, 2).k() }, { listOf(3, 4).k() }).p()
    }

    ForOptionT(ListK.monad())

    OptionT.just<ForListK, Int>(ListK.applicative(), 43).value().p()

}