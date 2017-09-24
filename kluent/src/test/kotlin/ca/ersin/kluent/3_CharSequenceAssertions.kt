package ca.ersin.kluent

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CharSequencSpec:Spek({
    // eg string

    val d = MyObj().d.toString()

    given(" MyObj's field character d") {
        on("assertions") {
            it("should") { d shouldEqualTo "d" }
            it("should") { d shouldStartWith "d" }
            it("should") { d shouldEndWith "D" }
            it("should") { d shouldContain "d" }
            it("should") { d shouldContainSome listOf("d") }
            it("should") { d shouldMatch "d" } // regex or string
            it("should") { d shouldMatch Regex(".") }
            it("should") { "".shouldBeEmpty() }
            it("should") { "".shouldBeNullOrEmpty() }
            it("should") { " ".shouldBeNullOrBlank() }
            it("should") { " ".shouldBeBlank() }
            it("should") { " ".shouldBeNullOrBlank() }
        }
    }

})