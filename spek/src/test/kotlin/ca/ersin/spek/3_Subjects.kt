package ca.ersin.spek

import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import kotlin.test.assertEquals

// a single class as the system under test, thus to remove boilerplate

object CalculatorSpec:SubjectSpek<Calculator>({

    subject { Calculator() } // the constructor is in the subject lambda, thus a new instance is provided per test scope

    beforeEachTest {
        println("Starting spec for " + this.javaClass.simpleName)
    }

    it("return sum") {
        assertEquals(1 + 2, subject.sum(1, 2))
    }

    it("return sub") {
        assertEquals(2 - 1, subject.subtract(2, 1))
    }
})

// when testing subclasses remove code duplication, AdvancedCalculator has a subclass of calculator

object AdvancedCalcSpec:SubjectSpek<AdvancedCalculator>({

    subject { AdvancedCalculator() }

    itBehavesLike(CalculatorSpec)

    describe("mult") {
        it("should return a mult b") {
            assertEquals(2 * 4, subject.mult(2, 4))
        }
    }
})

// advanced speck will do two before each for the calculator spec, then the three for advanced(2 for sum, sub, and
// one for mult)