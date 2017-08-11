// extension function built into the standard lib

val user:User? = null
var user1:User? = null

fun a(){
    if(user!=null){
//        user is not null
    }

//    but if using a var instead of val
    if(user1!=null){
        // user might be null, another thread might rewrite the value it might change
    }

    // so we use let
    user?.let {  // reads the variable once, in the block use variable as many times
        // as you want, read once is important because...
        // it == user not null
    }

    class Foo{
        @Volatile var user:User? = null // volatiles are expensive to read/write

        fun doS(){
            user?.let { user -> // no need to declare the local yourself
                // user not null, only read once
            }
        }
    }
}

// see
// .apply   Calls the specified function block with this value as its receiver and returns this value.
// .run     Calls the specified function block and returns its result. or with this value as its receiver and returns its result.
// .also    Calls the specified function block with this value as its argument and returns this value.
// .copy    Copy an object altering some of its properties, but keeping the rest unchanged.

// can use let with functions too
fun b(){
    fun hasReturnVal():Boolean =true // allows you to use the result of the method without converting
    // it to local scope
    hasReturnVal().let { theReturnVal -> if (theReturnVal) {} else {}}
}