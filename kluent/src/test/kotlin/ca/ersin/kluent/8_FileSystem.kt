package ca.ersin.kluent

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object FileSysSpek:Spek({

    given(" a file system") {

        val f = MyObj().g

        on("checking file properties") {
            it("should not exist") { f.shouldNotExist() }
            it("should not be a dir") { f.shouldNotBeDir() }
            it("should not be a file") { f.shouldNotBeDir() }
            it("should not have an extension") { f.shouldNotHaveExtension("txt") }
            it("should not have a name") { f.shouldNotHaveName("filename") }
        }
    }
})