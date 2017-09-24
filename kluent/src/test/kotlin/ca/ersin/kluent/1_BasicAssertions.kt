package ca.ersin.kluent

import org.amshove.kluent.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

class BasicSpec:SubjectSpek<MyObj>({

    subject { MyObj() }

    given("MyObj") {

        // nested givens

        given("field a") {
            on("a comparision of value with b") {
                it("should be equal") { subject.a shouldEqual subject.b }
            }
        }

        given("field b") {
            on("a comparision of value with a") {
                it("should be equal") { subject.b shouldEqual subject.a }
            }
        }

        // or generic top level

        on("a comparision of fields a and b") {
            it("should be equal to each others value") {
                subject.a shouldEqual subject.b
            }
            it("should be equal to each others reference") {
                subject.a shouldBe subject.b
            }
        }
        on("a comparision of fields a and b to a type") {
            it("should compare a to be equal to list") {
                subject.a shouldBeInstanceOf List::class
            }
            it("should compare b to be equal to list") {
                subject.b shouldBeInstanceOf List::class
            }
        }
        on("nullability checks with variables a and b") {
            it("should enforce that a is not null") {
                subject.a.shouldNotBeNull()
            }
            it("should enforce that b is not null") {
                subject.b.shouldNotBeNull()
            }
        }
        on("true true") {
            it("should be true") {
                true.shouldBeTrue()
            }
        }

    }
})