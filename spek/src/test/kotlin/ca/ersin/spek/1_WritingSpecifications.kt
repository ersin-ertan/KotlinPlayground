package ca.ersin.spek

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

// spek allows two styles of tests: given, on, it     or      describe, it

// given, on, it - classic style,
// four components - name: class name indicating what were testing,
// given: context, conditions under im testing, one to many per name
// on: action, executing for the test to later verify, one to many per given
// it: defines actual tests, verified once the action is execution, one to many per on


// describe, it - jasmine and mocha style with three minimum parts
// name: class name, what we are testing
// describe: defines context, can nest describes, each should provide more context
// it: defines the actula tests, verified onece the action is executed, one to many for describe

object SimpleSpec:Spek({
    describe("a calculator") {
        val calculator = Calculator()

        on("addition") {
            val sum = calculator.sum(1, 2)

            it("should return the result of the sum of the first and second") {
                assertEquals(1 + 2, sum)
            }
        }

        on("subtraction") {
            val sub = calculator.subtract(2, 1)

            it("should return the result of the subtraction of the first minus second") {
                assertEquals(2 - 1, sub)
            }
        }
    }
})

// no built assertion frameworks try org.jetbrains.kotlin:kotlin-test, HamKrest, Expekt, Kluent(best)