package reference.keywordAndOperators

import kotlin.reflect.KProperty

// Soft keywords - act as keywords in applicable contexts, but as identifiers in other contexts

interface Interfa {
    fun m() = println(0)
}


fun main(args:Array<String>) {

    delegation()

    // catch, and finally
    try {
    } catch (e:Exception) {
    } finally {
    } // always executed when try completes

    // constructor
    class Con { // for primary or secondary
        constructor()
        constructor(int:Int)
    }

    annotationUseSite()

//    dynamic // for js, not jvm

//    delegate, field, file, are both annotation use-site targets

    // get and set
    class DeclareGet {
        var v
            get() = 2
            set(value) {}
    }

    // import

    // where
    whereGenericConstraints()


}

fun whereGenericConstraints() {
    // set of all possible types that can be substituted for a given type parameter may be restricted by generic
    // constraints

    // upper bounds
    fun <T:Comparable<T>> sort(list:List<T>) {}

    sort(listOf(1, 3)) // int is a subtype of Comparable<Int>
//    sort(listOf(HashMap<Int,String>())) error: hashmap<Int, String> is not a subtype of Comparable<HashMap<Int,String>>

    // default upper bound(if none specified) is Any?. Only one upper bound can be specified inside the angle brackets.
    // if the same type parameter needs more than one upper bound, we need to separate where-clause

/*    fun <T> cloneWhenGreater(list:List<T>, threshold:T):List<T>
            where T:Comparable,
                  T:Cloneable {
        return list.filter { it > threshold }.map { it.clone() }
    }*/

    // not working
}

fun annotationUseSite() {
    // delegate, file, field, get, param, property, receiver, set, setparam

    // when annotating a property or primary constructor parameter, multiple java elements are generated from the
    // corresponding kotlin element, thus multiple possible locations for the annotation in the generated java bytecode
    // to specify how the annotation should be generated, use the following syntax
}


fun delegation() {
    open class A:Interfa {
        override fun m() = println(1)
    }

    open class AA:Interfa {
        override fun m() = println(2)
    }

    class D:Interfa {
        operator fun getValue(interfa:Interfa, property:KProperty<*>):Interfa {
            return A() // todo replace this
        }

        operator fun setValue(interfa:Interfa, property:KProperty<*>, interfa1:Interfa) {
        }

    }

    class B(inter:Interfa):Interfa by inter {
        var d:Interfa by D()
        fun n() = m()

    }

    B(A()).n()
    B(AA()).n()

    // delegating the implementation of the interface function to the delegate class

    // we can also delegate a property, see classes and objects for delegated properties
}
