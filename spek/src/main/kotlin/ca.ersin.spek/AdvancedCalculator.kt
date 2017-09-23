package ca.ersin.spek

class AdvancedCalculator:Calculator() { // only interfaces can be delegated

    fun mult(a:Int, b:Int) = a * b
}