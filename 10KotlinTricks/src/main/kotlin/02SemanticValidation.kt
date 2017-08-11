// java we are aggressive and check for null and the requirements of the value
// kotlin null is built into the type system

fun join(sep:String, strings:List<String>):String{
    require(sep.length < 2){
        throw IllegalArgumentException("sep.length < 2: " + sep)
    } // we pass it a lambda
    return "test"
}

fun types(){
    require(true) // IllegalArgugmentException
    requireNotNull(true)

    check(true) // IllegalStateException
    checkNotNull(true)

    assert(true) // AssertionError
}