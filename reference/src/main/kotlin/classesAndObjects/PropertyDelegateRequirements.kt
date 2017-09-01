package classesAndObjects

import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Property delegate requirements

// read only property(val), a delegate has to provide a function named getValue that takes params
// thisRef - same or supertype of the property owner(for extension properties - type being extended)
// property - must be of type KProperty<*> or its subtype

// the function must return the same type as the property or subtype


// for mutable properties(var) a delegate has to additionally provide a function name setValue that takes the paras
// thisRef - same as for getValue()
// property - same as for getValue()
// new value - must be of the same type as a property or its supertype


// getValue and setValue functions may be provided either as member functions of the delegate class or extension
// functions. Extension are good when you need to delegate a property to an object which doesn't originally provide
// these functions. Both functions need to be marked with the operator keyword.

// the delegate class may implement one of the interfaces ReadOnlyProperty and ReadWriteProperty containing the required
// operator methods. These interfaces are declared in the Kotlin standard lib:

interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef:R, pproperty:KProperty<*>):T
}

interface ReadWriteProperty<in R, T> {
    operator fun getValue(thisRef:R, pproperty:KProperty<*>):T
    operator fun setValue(thisRef:R, pproperty:KProperty<*>, value:T)
}

//Translation Rules - under thhe hood for every delegated property, the kotlin compiler generates an auxiliary
// property and delegates to it. The Property prop the hidden property prop$delegate is generated, and the code
// of the accessors simply delegates to this additional property

class C {
//    var prop:Type by MyDelegate()
}

// following code is generate for above
/*
class C {
    private val prop$delegate = MyDelegate()
    var prop: Type
        get() = prop$delegate.getValue(this, this::prop)
    set(value: Type) = prop$delegate.setValue(this, this::prop, value)
}*/

// compiler provides all teh necessary info about prop in the arguments: first arg this refers to an instance of
// the outer class C and this::prop is a reflection object of the KProperty type describing prop itself

//syntax of this::prop to refer a bound callable reference in the code directly is available only in 1.1


// Providing a delegate
// define the provideDelegate operator to extend the logic of creating the object which the property implementation
// is delegated. If the object used on the rright hand side of by defines provideDelegate as a member or extension function,
// that function will be called to create the property delegate instance.

// One of the possible use cases of provide delegate is to check property cconsistency when the property is created,
// not only on the getter/setter

// if you want to check the property name before binding:

/*
object ResourceID {
    val image_id:Int = 0
    val text_id:Int = 0
}

class MyUI {
    val image by bindResource(ResourceID.image_id)
    val text by bindResource(ResourceID.text_id)
}

class ResourceLoader<T>(id:ResourceID<T>) {
    operator fun provideDelegate(thisRef:MyUI, prop:KProperty<*>):ReadWriteProperty<MyUI, T> {
        checkProperty(thisRef, prop.name)
    }

    private fun checkProperty(thisRef:MyUI, name:String) {}
}

fun <T> bindResource(id:ResourceID<T>):ResourceLoader<T> {}*/

// not sure what's going on here

// the parameters of provideDelegate aret he same for getValue

//The provideDelegate method is called for each property during the creation of the MyUI instance, and performs
        // the necessary validation right away

//without this ability to intercept the binding between the pproperty and its delegate, to achieve the same functionality
// you'd have to pass the property name explicitly, which ins't very convenient

// Checking the property name without "provideDelegate" functionality
//class MyUI {
//    val image by bindResource(ResourceID.image_id, "image")
//    val text by bindResource(ResourceID.text_id, "text")
//}
//
//fun <T> MyUI.bindResource(
//        id: ResourceID<T>,
//        propertyName: String
//): ReadOnlyProperty<MyUI, T> {
//    checkProperty(this, propertyName)
//    // create delegate
//}

//the generated code, the providDelegate method is called to initialize teh auxiliary prop$delegate property
// compared to the generated code for the property declaration, val prop:Type by MyDelegate() wit hthe generated code(when
// the provideDelegate method is not present):

//class C {
//    var prop: Type by MyDelegate()
//}
//
//// this code is generated by the compiler
//// when the 'provideDelegate' function is available:
//class C {
//    // calling "provideDelegate" to create the additional "delegate" property
//    private val prop$delegate = MyDelegate().provideDelegate(this, this::prop)
//    val prop: Type
//        get() = prop$delegate.getValue(this, this::prop)
//}
//Note that the provideDelegate method affects only the creation of the auxiliary property and doesn't affect the code
// generated for getter or setter.