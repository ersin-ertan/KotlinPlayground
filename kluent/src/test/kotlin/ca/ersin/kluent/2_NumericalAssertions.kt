package ca.ersin.kluent

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

object NumericalSpek:Spek({

    beforeGroup {

        val listFirst = MyObj().a[0]

        given("list a's first value") {
            on("numerical equality") {
                it("should equal 1") {
                    listFirst shouldEqualTo 1
                }
            }
            on("a numerical comparision") {
                it("should be less than 2 ") {
                    listFirst shouldBeLessThan 2
                }
                it("should be greater than 0 ") {
                    listFirst shouldBeGreaterThan 0
                }
                it("should be greater or equal to 1") {
                    listFirst shouldBeGreaterOrEqualTo 1
                }
            }
            on("a range comparision") {
                it("shohuld be in -1..1") {
                    listFirst shouldBeInRange -1..1
                }
            }
            on("a special case") {
                it("should be positive") {
                    listFirst.shouldBePositive()
                }
                it("should be not negative") {
                    listFirst.shouldBeNegative()
                }
            }
        }
    }

})