package ca.ersin.kluent

import org.amshove.kluent.shouldNotBeInYear
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import java.time.LocalDate

// assertions for java.time. LocalDateTime, LocalDate, LocalTime

// not all cases are covered but all methods work to tested datetype
// there are extension functions for x.years(), months, days, hours, minutes, seconds

// shouldBeAfter, shouldBeBefore, shouldBeOnOrAfter/Before, shouldBeOn, shouldNotBeOn, shouldBeIn, shouldBeInYear,
// shouldBe, a shouldBe 5.days() after myDate, shouldBeAtLeast, shouldBeAtMost, shouldBe ... before

val a = LocalDate.of(2012, 1,1)
object A:Spek({
   given(""){
       it(""){
           a shouldNotBeInYear 12 // raised issue/PR, doccumentation error not included
       }
   }
})