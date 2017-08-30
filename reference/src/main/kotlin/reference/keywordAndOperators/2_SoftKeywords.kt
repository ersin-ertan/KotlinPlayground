package reference.keywordAndOperators

import kotlin.reflect.KProperty

// Soft keywords - act as keywords in applicable contexts, but as identifiers in other contexts

interface Interfa{
    fun m() = println(0)
}


fun soft(){

    open class A:Interfa {
        override fun m() = println(1)
    }

    open class AA:Interfa {
        override fun m() = println(2)
    }

    class D:Interfa {
        operator fun getValue(interfa:Interfa, property:KProperty<*>):Interfa {
            return A() // todo repalce this
        }

        operator fun setValue(interfa:Interfa, property:KProperty<*>, interfa1:Interfa) {
        }

    }

    class B(inter:Interfa):Interfa by inter{
        var d:Interfa by D()
        fun n() = m()

    }

    B(A()).n()
    B(AA()).n()

    // delegating the implementation of the interface function to the delegate class

    // we can also delegate a property


}