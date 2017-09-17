import com.squareup.moshi.Moshi
import kategory.*
import okhttp3.OkHttpClient
import okhttp3.Request

// Handling Kotlin Exceptions with Kategory – A Functional Approach
// by Roberto Guerra on Sep 15, 2017

// https://spantree.net/blog/2017/09/15/kotlin-exception-handling-with-kategory.html


// Libs and aps that throw exceptions can lead to unexpected behaviour at runtime. We can use functional programming
// concepts to provide apis that give affordances at compile time about runtime failures.


// Limitations of traditional exception handling - gives user of the throwing apis little indication at compile time
// of the exceptions for runtime.

val URL = "http://gd2.mlb.com/components/game/mlb/year_2017/month_06/day_18/miniscoreboard.json"

fun scores():List<Game>? {
    val httpClient = OkHttpClient()
    val request = Request.Builder().url(URL).build()

    val response = httpClient.newCall(request).execute()

    if (response.isSuccessful) {
        val body = response.body()?.string()

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(MinisScoreboardResponse::class.java)

        val msr = jsonAdapter.fromJson(body)

        return msr?.data?.games?.game
    } else {
        return null
    }

    // we can't tell where the failure is
}

// because network issues can occur, we must resiliently handle outages by handling okhttp's exceptions - but we only
// find out the throws in production, or if we read the third party code, how about wrapping it all in a try-catch?

fun scoresWithTryCatch():List<Game>? {
    try {
        return scores()
    } catch (e:Exception) {
        println("an exception occurred while fetching from MLB")
        return null
    }

    // won't crash in production,, but will fail to alert us of the issue, thus front end using scores cant react to the
    // error appropriately
}

fun scoresWithErrorPropogation():List<Game>? {
    try {
        return scores()
    } catch (e:Exception) {
        println("an exception occurred while fetching from MLB")
        throw e // <-- note this line
    }

    // but back to the issue about type signature giving no indication ofc what exceptions can be thrown, every client
    // now needs to know and also throw the Exception so higher up on the stack can handle it
}


// Functional Exception Handling

// most statically typed functional languages provide two abstractions for error handling and exceptions: Either and Try
// Exceptions use Try, and Either for business logic
// Article uses Kategory to showcase Try

// Try will take care of catching the exception and returning it to the caller, specifics need not be known till the end
// of the call stack where we use the result


fun scoresWithTry():Try<List<Game>?> { // article didn't have the ?
    val httpClient = OkHttpClient()
    return Try.invoke {
        val request = Request.Builder()
                .url(URL)
                .build()

        val response = httpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val body = response.body()?.string()

            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(MinisScoreboardResponse::class.java)

            val msr = jsonAdapter.fromJson(body)

            msr?.data?.games?.game
        } else
            emptyList<Game>()
    }

    // now clients of scores can know from the type signature, that the operation may throw an exception and it must be
    // handled. If you don't the compiler will complain to the client(I'm not seeing this warning, perhaps the ?
    // inclusion cancels it out), we need a more functional interface for the rest of the application

    // Try internally represents the failure in its Failure subclass, success with Success
}

// Using functions that return a Try, typed exceptions make it more explicit, but how do we use it

//
//fun scores1(): Try<List<Game>> {
//    val httpClient = OkHttpClient()
//    return Try.invoke {
//        val request = Request.Builder()
//                .url(URL)
//                .build()
//
//        val response = httpClient.newCall(request).execute()
//
//        if (response.isSuccessful) {
//            val body = response.body()?.string()
//
//            val json = ObjectMapper().readValue(body, MinisScoreboardResponse::class.java)
//
//            json.newInstance().data?.games?.game
//        } else {
//            emptyList<Game>()
//        }
//    }
//}

class ObjectMapper {
    fun readValue(body:String?, java:Class<MinisScoreboardResponse>):Class<MinisScoreboardResponse>? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(java::class.java)

        val json = jsonAdapter.fromJson(body)
        return json
    }

}

fun main(args:Array<String>) {

    fun provideDefaultsWithGetOrElse() {

        // Provide defaults with getOrElse - used for retrieving the value of the Success, is null safe forcing us to provide
        // default value if Failure

        scoresWithTry().getOrElse { emptyList() }
    }

    fun processingErrorsWithFold() {

        // Processing errors with fold - similar to getOrElse, but can transform the successful value. Can access the exception
        // and determine the result value. The second argument to fold takes a function that has Success value as a param

        scoresWithTry().fold({ exception ->
            // do something with the exception
            emptyList<Game>()
        }, { games -> games?.filter { it.awayTeamName == "Cubs" } })
    }

    fun continueingOnWithRecover() {

        // Continuing on with recover - recover is similar to fold, but only allows us to map/transform the failure case.
        // If Success, then the lambda in the fold is ignored, useful when we want to return a different value depending
        // on the exception thrown, but we don't want to transform the Success.

        // Similar to getOrElse, but recover returns another instance of Try

        /*
          Try.Success(1).recover { 2 } shouldBe Try.Success(1)
          Try.Failure<Int>(Exception()).recover { 2 } shouldBe Try.Success(2)
        */
        // not sure why the 'shouldBe' call is not working
    }

    fun safeguardingWithForComprehensions() {

        // Safeguarding with for comprehensions - similar to Scala's, but naming is unintuitive for beginners. We can
        // use for comprehensions by directly invoking the monad factory on the Try type. We want to use this when we
        // need to do some operations on the Success value in a sequential manner

        Try.monad().binding {
            val s = scoresWithTry()
            val games = s.getOrElse { emptyList() }
            val g:MutableList<Game> = games!!.toMutableList()
            g.add(Game())
            yields(g)
        }

        // inside the binding, we don't worry about handling Failure, operations will no-op and binding will return Failure
        // Above will block until completion, but can be non blocking and use a variant of recover called recoverWith

        Try.monad().binding {
            val games = scoresWithTry().recoverWith { Try.pure(emptyList()) }.bind()
            val g: MutableList<Game> = games!!.toMutableList()
            g.add(Game())
            yields(g)
        }
    }

}