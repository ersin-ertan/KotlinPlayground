package ca.ersin.kluent

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CollectionSpec:Spek({

    given("MyObj intArray"){

        val ia = MyObj().e
        val m = MyObj().f

        on("assertion"){
            it("should"){ia shouldContain 1 }
            it("should"){ ia shouldContainSome intArrayOf(1)}
            it("should"){ ia shouldEqual intArrayOf(1)}
            it("should"){ ia.shouldNotBeEmpty()}
            it("should"){ ia shouldContainAll intArrayOf(1)}

            // maps
            it("should"){ m shouldHaveKey 1 }
            it("should"){ m shouldHaveValue "a"}
            it("should"){ m shouldContain (1 to "a")}
            it("should"){ m.shouldNotBeEmpty()}
        }
    }
})