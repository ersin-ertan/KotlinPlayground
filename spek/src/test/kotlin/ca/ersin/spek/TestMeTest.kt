package ca.ersin.spek

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

internal class TestMeTest:Spek({

    describe(" int to string"){

        val testMe = TestMe()

        on("int"){
            val output = testMe.i2s(1)

            it("should be 1 as a string"){
                assertEquals("1", output)
            }

        }
    }
})