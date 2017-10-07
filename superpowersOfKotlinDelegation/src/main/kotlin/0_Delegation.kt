// Superpowers of Kotlin Delegation
// Marcin Moskala
// http://slides.com/marcinmoskala/superpowers-of-kotlin-delegation-3-3

// What and why delegates - manage state for well known patterns without the creation of classes, functions, contexts

interface Presenter {
    fun present()
}

open class ConfrencePresenter(val conferenceName:String):Presenter {
    override fun present() {
        println("This is best practice")
    }

    companion object {
        fun create(name:String):ConfrencePresenter = ConfrencePresenter(name)
    }
}

class DroidConPresenter(val conferenceName:String):Presenter {

    private val conferencePresenter = ConfrencePresenter("CoolConf")

    override fun present() {
        conferencePresenter.present()
        println("for android")
    }

}

fun main(args:Array<String>) {

    val p0 = ConfrencePresenter("generic")
    val p1 = DroidConPresenter("droidcon")
    val p2 = IosconPresenter("ioscon")

    p0.present()
    p1.present()
    p2.present()
}

// if you extend a class instead, we are assuming the state, and operations of the extended class will not
// change, else our logic built off of the ConferencePresenter logic can function incorrectly

class IosconPresenter(conferenceName:String):ConfrencePresenter(conferenceName) {

    override fun present() {
        super.present()
        println("for ioscon")
    }
}

// Thus, delegation is best used when the class is final(not allowing extension), it's private, or not designed
// for inheritance
// Or, if there is no is-a relationship with the inheriting class, or you need a portion of the functionality

// There are multiple ways to initialize delegation

class NewPresenter:Presenter by ConfrencePresenter("New con") // hard coded value
class NewPresenter1(confName:String):Presenter by ConfrencePresenter(confName) // input value
class NewPresenter4(presenter:Presenter):Presenter by presenter // generic presenter

class NewPresenter5(presenter:Presenter = ConfrencePresenter("ConConf")):Presenter
by presenter // generic presenter with default value

val confPresenter = ConfrencePresenter("HelloConf")

class NewPresenter2:Presenter by confPresenter // pre created value

// similar to above
class NewPresenter3:Presenter by ConfrencePresenter.create("chillConf") // factory created value

