package videos

import datatypes.p
import kategory.*

/*
* Functional Programming in Kotlin with Kategory
* Raul Raja & Paco Estevez
* https://www.youtube.com/watch?v=IL5XzaCMKpQ
* */

/*
* A library for typed FP in Kotlin with type classes and data types
*
* Data type: many data type to cover general uses cases
* error handling: option, try, validated, either, ior
* collections: listKW, sequenceKW, mapKW, setKW
* rws: reader, writer, state
* transformers: readerT, writerT, optionT, stateT, eitherT
* evlauation: eval, trampoline, free, functionN
* effects: io, free, observableKW
* others; coproduct, coreader, const
* */

fun syntaxExamples() {
    Option(1).map { it + 1 }
//    Try { throw RuntimeException("Boom!") }.map { it + 1 }
    val a = Either.Right(1)
    val b = 1.right()
    print(a == b)
}

// Applicative Builder - abstract over arity when computing over independent operations
// can be using futures, whatever. Generalized for all type constructor where the applicative is available

// code example not working, going to site examples
//data class Profile(val id: Long, val name: String, val phone: Int)
//
//fun profile(maybeId: Option<Long>, maybeName: Option<String>, maybePhone: Option<Int>): Option<Profile> =
//        Option.applicative()
//                .map(maybeId, maybeName, maybePhone,
//                        { (a, b, c) -> Profile(a, b, c) }
//                )
//
//val myProfile = profile(1L.some(), "Will Smith".some(), 23408530.some())

// ex 3 invocations as remote/local services returning different results in the same computation context
// of Option

fun profileService(): Option<String> = Option("Will Smith")
fun phoneService(): Option<Int> = Option(843912057)
fun addressService(): Option<List<String>> = Option(listOf("23 main st", "n3n 2n2", "ON"))

data class Profile(val name: String, val phone: Int, val address: List<String>)

fun usingApplicative() {

    val r: Option<Tuple3<String, Int, List<String>>> =
            Option.applicative()
                    .tupled(profileService(), phoneService(), addressService())
                    .ev()
    val myProfile = r.map { Profile(it.a, it.b, it.c) }

    // can map operations by abstracting over arity same way as tupled

    val myProfile1 =
            Option.applicative()
                    .map(profileService(), phoneService(), addressService(),
                            { (name, phone, address) -> Profile(name, phone, address) })
}

// more from website - Main Combinators

fun mainCombinators() {

// pure: lifts a value into the computational context of the type constructor
    Option.pure(1).p()

//    ap: apply a function inside the type constructors context
    println(Option(2) == Option.applicative().ap(Option(1), Option({ n: Int -> n + 1 })))

// other combinators: product, map, map2, map2Eval,
}


// For comprehensions: using coroutines to implement monadic bind
// for all monadic data types: futures, freemonades, option, try, either... can be computed in this
// for comprehensions style

//fun profile(maybeId: Option<Long>, maybeName: Option<String>, maybePhone: Option<Int>): Option<Profile> =
//        Option.monad().binding {
//            // coroutine starts
//            val id = maybeId.bind() // suspendend
//            val name = maybeName.bind() // suspendend
//            val phone = maybePhone.bind() // suspendend
//            yields(Profile(id, name, phone))
//            // coroutine ends
//        }
//// profile(2L.some(), "Will smith".some(), 234234.some())
//// again the code example doesn't work

// TODO - Finish video, got to confusing