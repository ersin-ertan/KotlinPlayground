class A

val lazyCreation by lazy { A() } // postpone object creation until first usage


// java way requires three objects, lock, value, get backing property
var actualObject:A? = null
val aLock:Any = Any()
val myObject:A
    get() {
        synchronized(aLock) {
            if (actualObject == null) actualObject = A()
            return actualObject!!
        }
    }


// lazy allows us to control the sync policy

val lazySyncPolicy by lazy(LazyThreadSafetyMode.NONE) { A() }

// LazyThreadSafteyMode. NONE, SYNCHRONIZED, PUBLICATION

// sync - locks used to ensure single thread can init the lazy
// pub - init func call sereval times on concurrent access to uninit lazy, but only first returned will be used as the value
// for all others
// non- no locks to sync, if instance is accessed from mumltiple threads, behaviour is undefined, thus use for single thread


// in android use lazy for views var v:TextView by lazy{ findViewById(R.id.tv)}
// declared and init in single place, properties are not lateinit and not nullable, properties are read only
// not sud are marked by compiler

// even better, define a func bindView(R.id.tv) that the lazy calls, and can be used for everything from texts, colours
// activity args

// heavy objects should be lazy too to speed up activity creation time, if you chain objects for creation then they should
// be lazy as well

fun main(args:Array<String>) {


}