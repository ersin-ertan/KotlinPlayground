import java.time.LocalDate
import kotlin.properties.Delegates

// delegates are observable and vetoable, observable acts like a normal proprety also invokes function literal from
// the parameter each time we set a new value

var name:String by Delegates.observable("Default") { property, oldValue, newValue ->
    println("$property, $oldValue, $newValue")
}

var list:List<LocalDate> by Delegates.observable(listOf(LocalDate.now())) { _, old, new ->
    if (new != old) notifyDataSetChanged()
}

fun notifyDataSetChanged() {}

// or drawer new value if value changed to open, call open drawer, else call close drawer
// or destroy and create presenters


var s:String by Delegates.vetoable("default") { _, old, new ->
    if (matchesRegex(new)) return@vetoable true else false
}

var networkName:String by Delegates.vetoable(getNameFromNetwork()) { _, old, new ->
    if (new != old) {
        networkCallChangeName(new)
        return@vetoable true
    } else println("thats the same name")
    return@vetoable false
}

fun networkCallChangeName(new:String) {}

fun getNameFromNetwork():String = ""


fun matchesRegex(string:String):Boolean = true


fun main(args:Array<String>) {

    name = "tom"
    name = "bill"

}