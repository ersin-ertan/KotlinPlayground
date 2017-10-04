package patterns

import kategory.*

// avoid exceptions with pure functional as they break referential transparency, leads to bugs at runtime.
// Example to do tasks, and fulfil preconditions

// arm nuke launcher, aim at target, launch nuke and impact target

fun armTodo():Nuke = TODO()
fun aimTodo():Target = TODO()
fun launchTodo(target:Target, nuke:Nuke):Impacted = TODO()


// Exceptions - naive implementation uses expections
fun armN():Nuke = throw RuntimeException("SystemOffline")

fun aimN():Target = throw RuntimeException("RotationNeedsOil")

fun launchN(target:Target, nuke:Nuke):Impacted = Impacted

// the function signatures don't provide that arm and aim may throw an exception

// they interrupt the program flow by jumping back to the caller like goto. Not consistent across async boundries
// Invoking async inside try/catch may not capture the exception thrown in a different thread
// may be abused in core libraries to signal events where throwable is an open hierarchy where you can catch
// more than you intended to

val a = try {
    armN()
} catch (e:Throwable) {
    // can be virtual machine error, out of memory error, thread death, linkage error, interrupted exception, ...
}

// exceptions are costly to create, Throwable.fillInStackTrace(), which records all info including current state of
// stack frame in current thread, because thread stack size is platform dependent(native call)
// Hidden performance costs of instantiating throwables

// new throwables each time, lazy by reusing throwables in method invocations, static with empty stack trace

// Exceptions are poor choice for functional programming when modeling absence, unknown business cases
// that result in alternate pathes, async boundries over unprincipled api callbacks, when there is no access
// to your source code

// Thus Kategory provides proper datatypes and type classes to represent exceptional cases via:
// option, try, either, monadError,


// Option - model the potential absence of a value

fun armO():Option<Nuke> = Option.None
fun aimO():Option<Target> = Option.None
fun launchO(target:Target, nuke:Nuke):Option<Impacted> = Option.Some(Impacted)

// easy to work with Option if lang supports monad comprehensions/syntax. Kategory has so for all datatypes
// which a Monad instance exists built on coroutines

fun attackOption():Option<Impacted> = Option.monad().binding {
    val nuke = armO().bind()
    val target = aimO().bind()
    val impact = launchO(target, nuke).bind()
    yields(impact)
}.ev()

val ao = attackOption() // None

// We could use option and forget about exceptions, but we are still unable to determine the reasons for why
// arm and aim returned empty values None. Option is only a good idea when we know that values may be absent
// but we don't care why. Option is unable to caputure exceptions, thus if it was thrown internally, it would
// bubble up and result in a runtime exception.


// Try - deal with potentially thrown exceptions outside the control of the caller. To be defensive about
// computation that may fail with runtime exceptions

fun armT():Try<Nuke> = Try { throw RuntimeException("system offline") }
fun aimT():Try<Target> = Try { throw RuntimeException("rotation needs oil") }
fun launchT(target:Target, nuke:Nuke):Try<Impacted> = Try { throw RuntimeException("missed by meteres") }

// exceptions are now controlled and caught inside of a try
fun t() {
    armT() // Failure(execption=RuntimeException: system offline
    aimT() // Failure ...
//    launchT() // failure ...

    // but, unlike Option, we can fold over the resulting value accessing the runtime exceptions

    val result = armT()
    result.fold({ ex -> "Boom:$ex" }, { "Success:$it" })
}

// Monad instances for Try are also available

fun attackTry():Try<Impacted> = Try.monad().binding {
    val nuke = armT().bind()
    val target = aimT().bind()
    val impact = launchT(target, nuke).bind()
    yields(impact)
}.ev()

val at = attackTry() // Failure(RuntimeException("system offline"))

// Try give us the ability to control both Success and Failure cases, but still nothing in the function signatures
// to indicate the type of exception. Subject to guess what exception is using when expressions, or runtime lookups
// over the unsealed hierarchy of Throwable.

// All exceptions thrown in our example are known, so no point of modeling with Exception.
// Redefine functions to express that their result is not just a nuke, target, or impact, but those potential values
// or exceptional ones


// Either - dealing with known alternate path we model return types as Either. Represents presence of Left or
// Right value. Convention Left is exception, and Right is success, like fold.

sealed class NukeException {
    object SystemOffline: NukeException()
    object RotationNeedsOil: NukeException()
    data class MissedByMeters(val meters:Int): NukeException()
}

object Nuke
object Target
object Impacted

typealias SystemOffline =  NukeException.SystemOffline
typealias RotationNeedsOil =  NukeException.RotationNeedsOil
typealias MissedByMeters =  NukeException.MissedByMeters

// this type definition is commonly known as an Algebraic data type or sum type in most FP languages.
// Encoded using sealed hierarchies in kotlin, which are declarations of a type and all possible states
// Once the ADT is defined to model our known errors, we can redefine our functions

fun armE():Either<SystemOffline, Nuke> = Either.Right(Nuke)
fun aimE():Either<RotationNeedsOil, Target> = Either.Right(Target)
fun launchE(target:Target, nuke:Nuke):Either<MissedByMeters, Impacted> = Either.Left(MissedByMeters(5))

// also with a monad instance for either the same way for option and try. BUT the type sinatures remain unchanged.
// All values on the left side assume to be Right biased and a Left value is found the computation short curcuits
// producing a result that is compatible with the function type signature

fun attackEither():Either<NukeException, Impacted> =
        Either.monad<NukeException>().binding {
            val nuke = armE().bind()
            val target = aimE().bind()
            val impact = launchE(target, nuke).bind()
            yields(impact)
        }.ev()

val ae = attackEither() // Left(MissedByMeters(4))

// How can we furthher generalize error handling and write code in a way that is abstract from the actual datatypes.
// Via typeclasses, emulated higher kinds and higher order abstractnsio we can rewrite in a fully polymorphic way


// MonadError - type class allowing us to handle error cases inside monadic contexts(either, try, option)
// Allow us to code focusing on the behaviours and not the datatypes that implements them.

fun m() {
    monadError<OptionHK, Unit>()
    monadError<TryHK, Throwable>()
    monadError<EitherKindPartial<NukeException>, NukeException>()
}

// Lets rewrite as a polymorphic function that will work over any datatype for which a MonadError instance exists.
// olymorphic code in Kategory is based on emulated Higher Kinds as described in Lightweight higher-kinded polymorphism
// and applied to Kotlin, a lang which does not yet support Higher Kinded Types.
//
inline fun <reified F> arm(ME:MonadError<F, NukeException> = monadError()):HK<F, Nuke> = ME.pure(Nuke)
inline fun <reified F> aim(ME:MonadError<F, NukeException> = monadError()):HK<F, Target> = ME.pure(Target)
inline fun <reified F> launch(target:Target, nuke:Nuke, ME:MonadError<F, NukeException> = monadError()):
        HK<F, Impacted> = ME.raiseError(MissedByMeters(5))

// we can now express the same program as before in a fully polymorphic context

inline fun <reified F> attack(ME:MonadError<F, NukeException> = monadError()):HK<F, Impacted> =
        ME.binding {
            val nuke = arm<F>().bind()
            val target = aim<F>().bind()
            val impact = launch<F>(target, nuke).bind()
            yields(impact)
        }
// since arm and bind are operations that don't depend on each other we don't need the monad comprehensions

inline fun <reified F> attack1(ME:MonadError<F, NukeException> = monadError()):HK<F, Impacted> =
        ME.tupled(aim(), arm()).flatMap(ME, { (nuke, target) -> launch<F>(nuke, target) })


fun main(args:Array<String>) {
    val result = attack<EitherKindPartial<NukeException>>()
    val result1 = attack(Either.monadError())
    attack1<EitherKindPartial<NukeException>>()
}