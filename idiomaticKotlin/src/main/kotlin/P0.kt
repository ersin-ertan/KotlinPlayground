import java.time.LocalDate
import java.time.Year
import java.time.temporal.ChronoUnit

fun Any.p() = println(this)

data class Name(val first:String, val middle:String? = null, val last:String, var birthYear:Year? = null, var email:String? = null)

interface EmailChannel {
    fun send(email:String, code:String? = null)
}

object OutgoingEmailHandler:EmailChannel {

    override fun send(email:String, code:String?) {

        val testEmail = mapOf(
                "email" to email,
                "message" to if (code != null) "use code $code to save big" else "We have a promotion next week",
                "keywords" to listOf("smartShopper", "trending", "myCompany")
        )

        testEmail.p()
    }
}

data class Promotion(val code:String, val expiryDate:LocalDate) {
    val validRange = LocalDate.now().rangeTo(expiryDate)
}

fun getNullPromotion():Promotion? = null
fun getRealPromotion():Promotion? = Promotion("13UY M3", LocalDate.now().plus(4, ChronoUnit.DAYS))

fun main(args:Array<String>) {

    val name = Name("Joe", last = "Patroni")

    fun getAdjective() = "love"
    val userCache = listOf("joe", "tom")

    val greeting = when {
        userCache.contains(name.first.toLowerCase()) -> "Welcome back ${name.first}!"
        else -> {
            try {
                "Be ${getAdjective()} ${name.first}"
            } catch (e:Exception) {
                "Hi ${name.first}}"
            }
        }
    }

    fun String.getExcited() = this + "!!!!!1!"

    greeting.getExcited().p()

    // get more info

    name.apply {
        birthYear = Year.parse("1970")
        email = "joe@email.com"
    }

    name.p()

    val isOfAge = name.birthYear?.isBefore(Year.parse("2000")) ?: false

    if (isOfAge) {

        // email is visible for the scope only
        name.email?.let {
            // destructuring
            getRealPromotion()?.let { (code, expiry) ->
                // do if not null

                // send only if there is more than one day left on the promotion, because better conversions for next promotion
                if (LocalDate.now().isBefore(expiry.minus(3, ChronoUnit.DAYS)))
                    OutgoingEmailHandler.send(it, code)
            }
        }
    }
}

