package classesAndObjects

import gettingStarted.p

// delegation pattern - design pattern in oop for object composition to achieve code reuse as inheritance

// a derived class can inherit from an interface Base and delegate its public methods to a specified object

interface Base {
    fun print()
}

class BaseImp(val x:Int):Base {
    override fun print() = println(x)
}

class Derived(b:Base):Base by b // let object b fulfill the Base functions

fun main(args:Array<String>) {
    val b = BaseImp(10)
    Derived(b).print()

//    createAndFireWeapon()
    v2()
}

interface Weapon {
    fun attack():Int = 5
}

class Cannon:Weapon {
    override fun attack():Int = 10
}

class Mortar:Weapon {
    override fun attack():Int = 20
}

class WeaponSilo(var weapon:Weapon):Weapon by weapon {
    fun switchWeapor(newWeapon:Weapon) {
        weapon = newWeapon
    }
}

fun createAndFireWeapon() {

    // @see https://youtrack.jetbrains.com/issue/KT-83
    // the delegation is computed once, at compile time thus the of delegate doesn't reflect the change of implementation

    val ws = WeaponSilo(Cannon())
    println("Weapon ${ws.weapon.javaClass.simpleName} attacks for ${ws.attack()} damage")

    ws.weapon = Mortar() // why are these attacks stuck with 10 ?
    println("Weapon ${ws.weapon.javaClass.simpleName} attacks for ${ws.attack()} damage")

    ws.weapon = Mortar() // why are these attacks stuck with 10 ?
    ws.switchWeapor(Mortar())
    println("Weapon ${ws.weapon.javaClass.simpleName} attacks for ${ws.attack()} damage")

    // this will change the object reference, thus the attack changes
    var ws1 = WeaponSilo(Cannon())
    ws1 = WeaponSilo(Mortar())
    println("Weapon ${ws1.weapon.javaClass.simpleName} attacks for ${ws1.attack()} damage")
}


// still has the same problem, using a sealed abstract class doesn't change the delegate implementation
fun v2(){
    val ws = WeaponSilo1(WeaponS.Cannon())
    println("Weapon ${ws.weapon.javaClass.simpleName} attacks for ${ws.attack()} damage")

    ws.weapon = WeaponS.Mortar()
    println("Weapon ${ws.weapon.javaClass.simpleName} attacks for ${ws.attack()} damage")
}

class WeaponSilo1(var weapon:WeaponS):Weapon by weapon {}


sealed class WeaponS:Weapon {

    abstract override fun attack():Int

    class Cannon:WeaponS() {
        override fun attack():Int = 10
    }

    class Mortar:WeaponS() {
        override fun attack():Int = 20
    }
}