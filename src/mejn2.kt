import wis.Sex
import wis.toPerson

enum class SEX {
    FEMALE, MALE
}

class Person(val name: String, val surname: String, val sex: SEX) {

    override fun toString(): String {
        return "Person(name='$name', surname='$surname', sex='$sex')"
    }
}

fun toPerson(tmp: String): Person {
    val tmpList = tmp.split(";")
    val name = tmpList[0]
    val surname = tmpList[1]
    val sex = SEX.valueOf(tmpList[2])
    return Person(name, surname, sex)
}

fun task21() {
    val person1 = Person("Jan", "Kowalski", SEX.MALE)
    val person2 = Person("Anna", "Nowak", SEX.FEMALE)
    val person3 = Person("Tomasz", "Fromage", SEX.MALE)
    val person4 = ("Katrzyna;TMP;FEMALE").toPerson()
    val personList = mutableListOf(person1, person2, person3, person4)
    personList.forEach(::println)
}

class BankAccount() {
    var balance: Double = 0.0
        private set

    constructor(balance: Double) : this() {
        this.balance = balance
    }

    fun deposit(amount: Double) {
        balance += amount
    }

    fun withdraw(amount: Double) {
        balance -= amount
    }
}

abstract class Figure(val name: String = "Figure") {
    abstract fun area(): Double
    abstract fun perimeter(): Double

    open fun describe() {
        println("I am a figure named $name")
    }
}

class Circle(val radius: Double) : Figure("Circle") {
    override fun area(): Double {
        return Math.PI * radius * radius
    }

    override fun perimeter(): Double {
        return 2 * Math.PI * radius
    }

    override fun describe() {
        println("I am a circle with radius $radius")
    }
}

class Rectangle (val width: Double, val height: Double) : Figure("Rectangle") {
    override fun area(): Double {
        return width * height
    }

    override fun perimeter(): Double {
        return 2 * width + 2 * height
    }

    override fun describe() {
        println("I am a rectangle with width $width and height $height")
    }
}

class Square(val side: Double) : Figure("Square") {
    override fun area(): Double {
        return side * side
    }

    override fun perimeter(): Double {
        return 4 * side
    }

    override fun describe() {
        println("I am a square with side $side")
    }
}

fun main() {

}
