import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// those were built in delegates, we can create custom delegates, lets try mutable lazy
fun <A> mutableLazy(initializer:() -> A):ReadWriteProperty<Any?, A> = MutableLazy<A>(initializer)

private class MutableLazy<A>(val initializer:() -> A):ReadWriteProperty<Any?, A> {

    private var value:A? = null
    private var initialized = false

    override fun getValue(thisRef:Any?, property:KProperty<*>):A {
        synchronized(this) {
            if (!initialized) {
                value = initializer()
                initialized = true
            }
            return value as A
        }
    }

    override fun setValue(thisRef:Any?, property:KProperty<*>, value:A) {
        synchronized(this) {
            this.value = value
            initialized = true
        }
    }
}

class Mode


var mode : Mode by mutableLazy{ getDefaultMode()}

fun getDefaultMode():Mode {return Mode()}

// getDefaultMode can be heavy to load, and can change its value before first use, but will be the latest at call time

fun main(args:Array<String>) {

    val mutableLazy = mutableLazy { 1 }

//    println(mutableLazy.getValue(this, mutableLazy.vvalue)) // not sure how to use it
}