package videos

import kategory.Either

/*
* KotlinConf 2017 - Architectures Using Functional Programming Concepts
* Jorge Castillo
* https://www.youtube.com/watch?v=qI1ctQ0293o
*
* */

/*
* FP separation of concern(declarative computations vs runtime execution), purity, referential
* transparancy, push state aside...
*
* used to solve key problems for many systems
*
* Modeling error and success cases
* Asynchronous code + threading
* side effects
* dependency injection
* testing
* */

/*
* Vanilla Java approach: exceptions + callbacks
* Threads swollow exceptions, breaks referential transparancy
* Alternative1: result wrapper wih error enums
* Alt2;rxjava
* Alternative3: Either<Error, Success>
* Kategory: IO to wrap the IO computations and make them pure, computation's return type is explicit
* so, IO<Either<CharacterError, List<SuperHero>>> so IO wraps a computation that can return either a
* CharacterError or a List<SuperHero> but never  both.
*

fun getAllHeroes(service:HeroesServic9e, logger: Logger): IO<Either<CharacterError, List<SuperHero>>> =
        runInAsyncContext(
                f={queryForHeroes(service)},
                onError = { logger.log(it); it.toCharacterError().left()},
                onSuccess = { mapHeroes(it).right()},
                AC= IO.asyncContext()
        )


Use case: requires a double map to get to the result
fun getHeroesUseCase(service:HeroesService, logger:Logger): IO<Either<CharacterError, List<SuperHero>>> =
    getAllHeroesDataSource(service, logger).map{ it.map{discardNonValidHeroes(it)}}

Presentation logic
fun getSupreHeroes(view:superHeroesListView, service:HeroesService, logger:Logger) =
    getHeroesUseCase(service, logger).unsafeRunAsync{ it.map{maybbeHeroes ->
        maybeHeroes.fold(
            { error -> drawError(error, view)},
            { success -> drawHeroes(success, view)})}
    }

Now we break purity and tell it to do the computation, thus unsafeRunAsync call, when that is unfolleded
we get access to the IO to which we can unfolled to get the data

Effects applied here are not ideal because we are applying side effects on the presentation layer

Problem - we would perform unsafe effects on the edge of the sysems where our frameworks are coupled.
On a system with a frontend layer, it would be the wie impl

Solutions - lazy evaluation, deferred execution, declare the whole execution tree based on returning functions
By returning functions at all levels, you swap proactive evaluation with deferred execution

presenter(deps) = { deps -> useCase(deps) }
useCase(deps) = { deps -> dataSource(deps) }
dataSource(deps) = { deps -> deps.apiClient.getHeroes() }

Passing dependencies all teh way down at every execution leel can be painful
Can't we implicitly inject / pass them in a simple way to avoid manual passing

Dependency injection/passing with the Reader Monad(composable, can flatmap over it)
Wraps a computation with type (D)->A and enables composition over computations with that type
Where D are the computation's dependencies, for each of the above 3 executions, we store them in the
Reader Monad and compose them together using different readers
D is the reader context(dependencies)
It operations implicitly pass in the context to the next execution level
Think about the context as the dependencies needed to run the complete function tree(dependency graph)


Discovering the Reader Monad
Solves both concerns of defering computations at all levels and
injecting dependencies by automatically passing them across the different function calls

Reader<D, IO<Either<CharacterError, List<SuperHero>>>>
We start to die on the types a bit here, but a better solution is present

        explicit dependencies not needed anymore
fun getHeroes():
    Reader<GetHeroesContext, IO<Either<CharacterError, List<SuperHero>>> =
        Reader.ask<GetHeroesContext>().map({ ctx ->
            runInAsyncContext(
                f = { ctx.apiClient.getHeroes() },
                onError = { it.toCharacterError().left() },
                onSuccess = { it.right() },
                AC = ctx.threading
            )
        })

Reader.ask() lifts a Reade{ D -> D } so we ge access to D when mapping


use case now with three monads on the stack
fun getHeroesUseCase() = fetchAllHeroes().map { io ->
    io.map { maybeHeroes ->
        maybeHeroes.map { discardNonValidHeroes(it) }
        }
    }

prresenter code
fun getSuperHeroes() = Reader.ask<GetHeroesContext> ().flatMap(
    { ( _, view: SuperHeroesListView) -> // context deconstruction
        getHeroesUseCase().map({ io ->
            io.unsafeRunAsync { it.map { maybeHeroes ->
                maybeHeroes.fold(
                    { error -> drawError(error, view) },
                    { success -> drawHeroes(view, success))}
                }
             }
          })
       })

Complete computation tree deferred thanks to Reader
Completely pure computation since effects are still not run
When the moment for performing effects comes, you can simply run it by passing the context you want to use:

we perform unsafe effects on the view impl now
override fun onResume(){
    // presenter call
    getSuperHeroes().run(heroesContext) // getSuperHeroes returns a reader(deferred computation)

No longer pure, unfold position provided a given context
For testing, pass a different context which can be providing fake dependencies for ones we need to mock


How do we improve the nested types hell, because monads don't compose gracefully
Monads of the same type can flatMap easily, but not monads of different type

thus we use Monad Transformers to solve this.

Monad Transformers wraps monads to gift those with other monad capabilities(new behaviour)

We want ReaderT<EitherT<IO>>
EitherT(EitherTransformer) gives Either capabilities to IO
ReaderT gives Reader capabilites to EitherT<IO>

We create an alias for that composed type, with syntax:
typealias AsyncResult = ReaderT<EitherT<IO>>

AsyncResult<D,A>
which dose error handling, asynchrony, io operations, and dependency injection
This is generic to any application.

fun <D: SuperHeroesContext> fetchAllHeroes(): AsyncResult<D, List<SuperHero>> =
    AsyncResult.monadError<D>().binding { // binding are part of Monad comprehensions
        val query = buildFetchHeroesQuery()
        val ctx = AsyncResult.ask<D>().bind()
            runInAsyncContext(
                f = { fetchHeroes(ctx, query) },
                onError = { liftError<D>(it)},
                onSuccess = { liftSuccess(it)},
                AC = ctx.threading<D>()
            ).bind()
        }

Monad comprehensions are syntaxtic sugar related to do notation/for comprehensions
Sequentially different async statements by flatmaping the results from one to another, mimicing
sync style.

Because the result is lifted inside the context of the monad.
Monad bindings return an already lifted and flatMapped result to the context of the monad

use case, not the use case doesn't need to map three times just once
fun <D : SuperHeroesContext> getHeroesUseCase(): AsyncResult<D, List<CharacterDto>> =
    fetchAllHeroes<D>().map { discardNonValidHeroes(it) }

presenter
fun genSuperHeroes() : AsyncResult<GetHeroesContext, Unit> =
    getHeroesUseCase<GetHeroesContext>()
        .map { heroesToRederableModels(it) }
        .flatMap { drawHeroes(it) }
        .handleErrorWith { displayErrors(it) }

view impl
override fun onResum(){
    getSuperHeroes().unsafePerformEffects(heroesContext)
}
Again for testing, pass in a different context providing fake dependencies for object requiring mocks

Extras:
two advanced FP styles can be implmented using in Kategory

Tagless-Final: remove contrete monad types from your code(IO, Either, Reader) and depend just on behaviours
defined by typeclasses
Run your program later on passing in the implmentations you want to use for those behaviours on this execution


Free Monads: separates concerns about declaring the AST(abstract syntax tree) based on Free<S, A> in a
pure way, and interpreting it later using an interpreter.
Free is used to decouple dependencies, so it also replaces the need for dependency injection. Remember this
when defining the algebras.

*/