package videos

/*
* droidcon SF 2017 - Building a DSL… in Kotlin!
* Pablo Guardiola
* https://www.youtube.com/watch?v=kr8iWE4Jfhc
* */
/*
Key Concepts: composable, extensible, testable, integrates with current frameworks

DSL: language is composed of syntax and semantics(what it does when you execute the model)
Programming language, language nature, limited expressiveness, domain focus


private fun composing(element: UiObject): Boolean {
    val actions = pinchOut(100, 50)
            .swipeDown(30)
            .swipeRight(30)
            .pinchOut(100, 50)
            .swipeUp(100)
    return actions.validate(element).hasCompletedCorrectly()
}

Free Dsl - seperation of concerns using an AST and then using an interpreter
Define your algebra(operations)

@higherkind sealed class GestureDSL<A> : GestureDSLKind<A> {
    data class WithView<A>(val f:(UiObject) -> A): GestureDSL<A>()
    object Click: GestureDSL<Unit>()
    object DoubleTap: GestureDSL<Unit>()
    data class PinchIn(val percent: Int, val steps:Int):GestureDSL<Unit>()
    //
    companion object:FreeApplicativeApplicationInstance<GestureDSLHK>


}

Lift everything to the context of free: instantiating the object to do the operations
fun <A> withView(f:(UiObject) -> ):ActionDSL<A> = FreeApplicative.liftF(GestureDSL.WithView(f))
//
fun ActionDSL<Unit>.click():ActionDSL<Unit> = combine, com.pg.uigest.dsl.click())
fun click(): ActionDSL<Unit> = FreeApplicative.lift(GestureDSL.Click)

fun ActionDSL<Unit>.doubleTap():ActionDSL<Unit> = combine(this, com.pg.uigest.dsl.doubleTap())
fun doubleTap():ActionDSL<Unit>= freeApplicative.liftF(GestureDSL.DoubleTap)

fun ActionDSL<Unit>.pinchIn(percent: Int, steps:Int):ActionDSL<Unit> = combine(this, com.pg.uiges.dsl.pinchIn(percent, steps))
fun pinchIn(percent:Int, steps:Int):ActionDSL<UNit> = FreeApplicative.liftF(GestureDSL.PinchOut(percent, steps))

// this is all boiler plate, thus kategory will generate this code for you

Provide semantics with the interpreter

fun safeInterpreter(view: UiQbject()):FuntionK<GestureDSLHK, ValidatedKindPartial<NonEmptyList<GestureError>>> =
    object:FunctionK<GestureDSLHK, ValidatedKindPartial<NonEmptyList<GestureError>>>{
        override fun <A> invoke(fa:GestureDSLKind<A>): ValidateKind<NonEmptyList<GestureError>, A> {
            return when(g){
                is GestureDSL.Click -> view.click().validate({ GestureError.ClickError(view) })
             } as validatedKind<NonEmptyList<GestureError>, A>
         }
     }

fun loggingInterpreter(view:UiObject):FunctionK<GestureDSLHK, EitherKindPartial<GestureError>>> =
    object: FunctionK<GestureDSLHK, EitherKindPartial<GestureError>> {
        override fun <A> invoke(fa:GestureDSLKind<A>): EitherKind<GestureError, A> {
            println(fa)
            return safeInterpreterEither(view) as EitherKind<GestureError, A>
        }
    }

Execute the AST using the interpreter, how do we traverse the tree and get results?

unfold

fun <A> ActionDSL<A>.validate(view:UiObject): ValidatedNel<GestureError, A> =
    Try{
        this.foldMap(
                safeInterpreter(view),
                Validated.applicative<NonEmptyList<GestureError>>()).ef()
        }.fold({ GestureError.UnknownError(it).invalidNel()}, { it })

fun <A> validatedNel<GestureError, A>.hasCompletedCorrectly():Boolean = this.isValid
fun <A> ValidatedNel<GestureError, A>.errors():List<GestureError> = this.fold({ it.all }, { emptyList() })

private fun composingWithPlus(element:UiQbject, pointers:List<Array<motionEvent.PointerCoords>>):Boolean{
    val actions = pinchOut(75, 30) + swipeDown(30) + multiTouch(pointers)
    return actions.failFast(element).hasCompletedCorrectly()

}

Takeaways: composable actions, extensible, reusable using different interpreters, integration with cur stack

*/

