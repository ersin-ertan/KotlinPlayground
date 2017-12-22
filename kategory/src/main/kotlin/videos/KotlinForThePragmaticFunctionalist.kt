package videos

import kategory.*

/*
* KotlinConf 2017 - Kotlin for the Pragmatic Functionalist
* Paco Estévez García
* https://www.youtube.com/watch?v=s9oMED6ZikQ
*
* Data classes: plain info, stores specifics, closed and immutable by default, operations must be done
* externally
*
* Sealed Classes: closed inheritance, to indicate branching and execution, checked at compile time
*
*
*
* Inliner: copies a function in its call site, inline functions body is copied at compile time,
* all call sites and static dispatch are known at compile time, enable runtime generics(reified types)
*
*
*
* Extension functions: converts static functions into object methods, all call sites with static dispatch
* are known at compile time, can extend just for specific generic parameters
*
*
*
* Key features: extension interfaces, ad-hoc polymorphism for generic type constructors, imperative async,
* smarter boilerplate removal
*
*
*
* Extension Interfaces: implement any interface for already existing types, use the extension interface
* as a parameter, wip: automatic extension interface lookup
*
* */

interface Jsonable<A> {

    fun toJson(element: A): String
//    fun fromJson(json: String): A
}

//fun <A> jsonable(): Jsonable<A> = { } // lookup code here

class Gson<A> {
    fun toJsonString(element: A) = "abc"
//    fun fromJsonString(json: String):A {} // should have something like :A = ... return A()
}

class Employee

object EmployeeJsonable : Jsonable<Employee> {

    val gson = Gson<Employee>()

    override fun toJson(element: Employee): String = gson.toJsonString(element)
//    override fun fromJson(json: String): Employee = gson.fromJsonString(json)
}

class Company

//fun parser(company: Company, parser: Jsonable<Company> = jsonable<Company>()): String = parser.toJson(company)

// If were are looking up a type whoes generic doesn't exist, the compiler will tell us(not imported to current scope)
// If we need Jsonable<Company> or address or user, then we must have everything defined at the global level

// current implementation takes O(1) runtime for a map(for jsonable interfaces)
// but is a requested feature as compile time.

// Ideally we declare the parser for type A where A implements an extension function ...
// So long as the extension interface has been imported you can use it.
// This is introduced as Type Classes which enables compile time lookup and injection



/*
* Ad-hoc polymorphism for generic type constructors: functions that support any generic type constructor,
* express agnosticity to implementation, safe downcast from generic to implementation
* */

// Generic Type constructor
interface  Hk<F,A> // F is container/collection, A is content

// then replace F with a concrete container: with Option, ListWrap, or Try

// sealed class Option<A>:Hk<OptionHK, A> for some value, or null value
// data class ListWrap<A>(...):Hk<ListWrapHK, A>
// sealed class Try<A>:Hk<TrayHK, A> execute the function in a try catch block safely evaluating the result

// You can't create a type of F because it is a "tag" type, used only at compile time for generics
// A remains the type of the content

// We are only tagging the generic of the container, but how do we use it in a function

class User
// Returning generic type constuctors, our function gets the user by id which returns the type constructor
// based on F, but we already know the content which is a user

//fun <F> getUserById(id:String):Hk<F, User> = /* What goes here */

// Because F can be anything, list, try... how do we create one instance of it?
//
// Using Type classes and extension interfaces


// Extension Interface Factory: for a generic type A, I can create an F of A
interface Factory<F>{
    fun <A> create(element: A):Hk<F, A>
}

//fun <T> factory():Factory<T> = // lookup code

// Now getUserById depends on the type F implemented by a factory, using the global lookup and
// factory.create
fun getUser(id:String):User = User()
//fun <F> getUserById(id:String, factory:Factory<F> = factory<F>()):Hk<F, User> = factory.create(getUser(id))

// the problem is that F is contained in the higher type, we must safely downcast generic containers

// extension functions can be defined to require just some of the generic parameters, extension functions
// are resolved at compile time. We can use extension functions, because they know the correct type at runtime.
// The can do the substitution from the original type into the implementation functions that are used

// We define the function ev for down casting by extending the Hk<OptionHK, A>
fun <A> Hk<OptionHK, A>.ev() = this as Option<A>
fun <A> Hk<TryHK, A>.ev() = this as Try<A>

// Putting it all together
fun a(){
//    val a: Option<User> = getUserById("234").ev()
//    val b: Try<User> = getUserById("234").ev()
}

// both have a factory defined which are capable of generifying on the container rather than the content


/*
object OptionFactory:Factory<OptionHK>{
    fun <A> create(element:A):Hk<OptionHK,A> = Option.Some(element)
}

object TryFactory:Factory<TryHK>{
    fun <A> create(element:A):Hk<TryHK,A> = Try{ a }
}

 global lookup for the factories


 the HK concept as the container in not Kotlin exclusive, trying to get all Jvm langs involved to implement
 the generic HK type. See KindedJ. Share generic type constructors under the name Higher Kinded Types.

*/

// Becoming useful, Imperative async code, (coroutines to express sync looking code that is async/parallel
// for any existing framework and abstraction

// we need to provide a type class that defines a way for abstractions to be used within coroutines,
// to be awaited for

// Regular async code

//fun<F> getUserFriends(id:String):Observable<List<User>> =
//        getUserById(id).flatMap{ user ->
//            Observable.merge( user.friends.map{ friend ->
//                getUserById(friend.id)}
//            ).toList()
//        }
//

// Imperative async code, coroutinable is a type class and bindingE is the same as async
//fun <F> getUserFriends(id:Sring, coroutinable:Coroutinable<F> = coroutinable<F>()):Hk,F, List<User>> =
//        coroutinable.bindingE{
//            val user = getUserById(id).bind()
//            val fiendProfiles = user.friends.map{ getUserById(it.id).bind()}
//            yields(friendProfiles)
//}

// which receives a block function to wait for completion: getUserId.bind() same as flatMap so wait till
// user is available and continue execution on the next line
// Then user.friends.map which will wait to get the information for every user
// then we yield(friendProfiles) and return the result from the async code

// val a: Either<Exception, List<User>> = getUserFriends("234).ev()
// val b: Try<List<User>> = getUserFriends("234).ev()
// val c: ObservableKW<List<User>> = getUserFriends("234).ev()
// val d: IO<List<User>> = getUserFriends("234).ev()


// everything is generic to the container so long as it has coroutinable, no need to extend objects
// to add coroutinable, create an object that exists in global namespace to do the lookup to generate
// them

// but Coroutinable???? no its MonadError

// typealias Coroutinable<F> = MonadError<F> // which allows us to continue execution in a sequential way

// Effects with MonadError: thin extension interface that can be implemented by any existing framework and
// abstraction, with error handling, stack safety, threading and cancellation built in, current
// integrations with: rxjava, kotlin.coroutines, IO, potential integrations: commpletableFuture,
// kotlinx.coroutines, AsyncTask, ArchComponents, Kovenant

// Anything that is capable of doing sequential execution of async code can be generified using monadError
// We understand the abstraction so we can build on top of it, like error handling(like if you have
// an exception within this binding block wrap it and return a new type, recursive operations can ensure
// stack safety, we can run any block of code to background(coroutine context) and add cancellation)

// for simpler situations try other implementations and build them on top of each other:
// Implementations in Kategory:
/*
* Error handling: Option, Try, Validated, Either, Ior
* Collections: List, Sequence, Map, Set
* RWS: Reader, Writer, State
* Transformers, ReaderT, WriterT, OptionT, StateT, EitherT
* Evaluation: Eval, Trampoline, Free, Function0
* Others: Coproduct, Coreader, Const...
*
* For your own version see kotlin-metadata project on github that reads the info that the kotlin compiler
* puts on the bytecode to understand dataclasses, sealed classes, all the inheritors at compile time
* so we can use that to generate the code necessary to make the abstractions happen
*
* If we want to make a new version of ex. Either, use the generic constructor annotation @higherkind
* extension interfaces uses @derives say you want to implement a monad and you already have a data type
* like Observable that contain flatMap as a function, as long as the signature matches, we can create
* those instances
* @instance for global lookup
* Manipulation of immutable data & sealed classes kategory-optics, if you have nested values, you must
* copy each of the layers until you get to the modification point too much boilerplate
* thus optics will allow to change the variable Company to change all the addresses to all the users
* to be camelCase by passing the function which is the composition of optics, no boilerplate
* */