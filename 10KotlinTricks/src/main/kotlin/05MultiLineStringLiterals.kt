val stringJava = "a\nb\nc"
val stringJavaOr = "a\n" +
        "b\n" +
        "c"

val stringKotlin = """
    a
     b
      c"""

// but will preserve the indents thus

val stringKotlin1 = stringKotlin.trimIndent() // will trim the left most indent
// and keep the rest, or use

val stringKotlin2 = """|a
                       |b
                       |c""".trimMargin() // which will default to the |
// string interpolation still works

fun p(){
    println(stringKotlin2)
}