package ca.ersin.spek

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xit

// scopes:
// test - test methods like in JUnit, all assertions are done in this scope using it to define a test scope
// group - arbitrary number of nested scopes for better grouping of tests
// action - action that will be performed and may contain any number of test scopes, each test as an assertion
// to validate the expected behaviour of the action perfformed

// can also use given and context to create group scopes(group scopes aree eagerly evaluated)

// Fixtures  running arbitrary code before and after a group and test is executed, but not within action scopes
//

private val groupA = "Group_AAA"
private val groupB = "Group_BBB"

object FixtureSpec:Spek({
    describe(groupA) {

        beforeGroup {
            println("\tbefore " + groupA)
        }

        beforeEachTest {
            println("\tbefore each test in " + groupA)
        }

        context(groupB) {
            // nested

            beforeEachTest {
                println("\tbefore each test in " + groupB)
            }

            it("should work") {
                println("\tstarting it test in " + groupB)
            }

            afterGroup {
                println("\tafter group in group " + groupB)
            }
        }

        it("do more") {
            println("\tstarting it test in " + groupA)

        }

        xit("will ignore this test"){} // anything prefixed with x will be ignored

        afterEachTest {
            println("\tafter each test in " + groupA)
        }

        afterGroup {
            println("\tafter group in group " + groupA)
        }
    }
})