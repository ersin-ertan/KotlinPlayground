package classesAndObjects.delegatedProperties

// see@ https://stackoverflow.com/questions/45970738/kotlin-delegate-property-by-lazy-that-is-thread-local

class ThreadLocalLazy<T>(val provider:() -> T):Lazy<T> {

    private val threadLocal = object:ThreadLocal<Lazy<T>>() {
        override fun initialValue():Lazy<T> = lazy(LazyThreadSafetyMode.NONE, provider)
    }

    override val value get() = threadLocal.get().value
    override fun isInitialized() = threadLocal.get().isInitialized()
}

fun <T> threadLocalLazy(provider:() -> T) = ThreadLocalLazy(provider)