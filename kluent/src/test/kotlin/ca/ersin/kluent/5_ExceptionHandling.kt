package ca.ersin.kluent

import org.amshove.kluent.AnyException
import org.amshove.kluent.shouldThrow
import org.amshove.kluent.withCause
import org.amshove.kluent.withMessage
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import java.io.IOException

object Exceptions:Spek({

    val ex = { MyObj().ex() }

    given("") {
        on("") {
            it("") { ex shouldThrow AnyException }
            it("") { ex shouldThrow RuntimeException::class }
            it("") { ex shouldThrow RuntimeException::class withMessage "" }
            it("") { ex shouldThrow RuntimeException::class withCause IllegalArgumentException::class }

            // for chained type and message
            val func = { throw IllegalArgumentException("hello", IOException()) }
            it("") { func shouldThrow IllegalArgumentException::class withCause IOException::class withMessage "hello" }
        }
    }

})