package essentialScala

// Algebraic data types

// Goal is to translate data descriptions into code

// Model data with logical ors and logical ands

// A website visitor is: logged in or anonymous

// A logged in user has: an ID and an email address

// Structure of the code follows the structure of the data

// Two patterns:
// product types (and)
// sum types (or)

class A_and(b:B_and, c:C_and) // A has B and C

class B_and
class C_and

sealed class A_or
class B_or:A_or()
class C_or:A_or()

interface Visitor {
    val id:Int
}

class Anonymous(override val id:Int):Visitor
class User(override val id:Int, val email:String):Visitor

sealed class Calculation
class Success(val value:Int):Calculation()
class Failure(val message:String):Calculation()

// Summary
// structure dat with logical ands and ors
// Structure of the code follows the structure of the data
// these are called algebraic data types(option, try, list)
// code follows immediately from structure of the data
