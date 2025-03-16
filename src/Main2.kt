package wis

enum class Sex {
    MALE, FEMALE
}

data class Person(
    val name: String,
    val surname: String,
    val sex: Sex
) {
    override fun toString(): String {
        return "Person(name='$name', surname='$surname', sex=$sex)"
    }
}

fun String.toPerson(): Person {
    val parts = this.split(";")
    require(parts.size == 3)
    val (name, surname, sexString) = parts
    return Person(name, surname, Sex.valueOf(sexString))
}

fun taskI() {
    val people = listOf(
        "Jan;Kowalski;MALE".toPerson(),
        "Anna;Nowak;FEMALE".toPerson(),
        Person("Piotr", "Zieliński", Sex.MALE),
        Person("Ewa", "Kowalczyk", Sex.FEMALE)
    )
    println("Lista osób:")
    people.forEach { println(it) }
    val (n, s, sx) = people[0]
    println("Destrukturyzacja osoby[0]:")
    println("Imię: $n, Nazwisko: $s, Płeć: $sx")
}

class BankAccount(private var _balance: Double) {
    val balance: Double
        get() = _balance

    fun withdraw(amount: Double) {
        if (amount < 0) throw IllegalArgumentException()
        if (amount > _balance) throw IllegalArgumentException()
        _balance -= amount
    }

    fun payInto(amount: Double) {
        if (amount < 0) throw IllegalArgumentException()
        _balance += amount
    }

    constructor() : this(0.0)
}

fun taskII() {
    val konto1 = BankAccount(100.0)
    val konto2 = BankAccount()
    println("--- Bank Accounts ---")
    println("Konto1 -> Balance: ${konto1.balance}")
    println("Konto2 -> Balance: ${konto2.balance}")
    konto1.withdraw(50.0)
    konto2.payInto(200.0)
    println("Po operacjach:")
    println("Konto1 -> Balance: ${konto1.balance}")
    println("Konto2 -> Balance: ${konto2.balance}")
}

private const val READ = 0b100
private const val WRITE = 0b010
private const val EXECUTE = 0b001

class Permission(private var permissions: Int = 0) {
    operator fun plusAssign(permission: Int) {
        validate(permission)
        permissions = permissions or permission
    }

    operator fun minusAssign(permission: Int) {
        validate(permission)
        permissions = permissions and permission.inv()
    }

    operator fun inc(): Permission {
        this += WRITE
        return this
    }

    operator fun dec(): Permission {
        this -= WRITE
        return this
    }

    operator fun contains(permission: Int): Boolean {
        return (permissions and permission) == permission
    }

    private fun validate(permission: Int) {
        val allowed = listOf(
            READ, WRITE, EXECUTE,
            READ or WRITE, READ or EXECUTE,
            WRITE or EXECUTE, READ or WRITE or EXECUTE
        )
        if (permission !in allowed) throw IllegalArgumentException()
    }

    override fun toString(): String {
        if (permissions == 0) return "No permissions"
        val result = buildList {
            if ((permissions and READ) == READ) add("READ")
            if ((permissions and WRITE) == WRITE) add("WRITE")
            if ((permissions and EXECUTE) == EXECUTE) add("EXECUTE")
        }
        return result.joinToString(", ")
    }
}

fun taskIII() {
    var perm = Permission()
    println("--- Manipulacja uprawnieniami ---")
    println("Początkowe: $perm")
    perm += WRITE
    println("Po += WRITE: $perm")
    perm += READ
    println("Po += READ: $perm")
    println("perm ma READ? -> ${READ in perm}")
    ++perm
    println("Po ++: $perm")
    perm -= READ
    println("Po -= READ: $perm")
    --perm
    println("Po --: $perm")
    perm += EXECUTE
    println("Po += EXECUTE: $perm")
    try {
        perm += 0b1000
    } catch (e: IllegalArgumentException) {
        println("Błąd: ${e.message}")
    }
}

/*===================*/
/*      TASK IV      */
/*===================*/
abstract class Figure(val name: String = "Figure") {
    abstract fun area(): Double
    abstract fun perimeter(): Double

    open fun describe() {
        println("I am a figure named $name.")
    }
}

// Square
class Square(val side: Double) : Figure("Square") {
    override fun area(): Double = side * side
    override fun perimeter(): Double = 4 * side
}

// Rectangle
class Rectangle(val width: Double, val height: Double) : Figure("Rectangle") {
    override fun area(): Double = width * height
    override fun perimeter(): Double = 2 * (width + height)
}

// Circle
class Circle(val radius: Double) : Figure("Circle") {
    override fun area(): Double = Math.PI * radius * radius
    override fun perimeter(): Double = 2 * Math.PI * radius
}

// Extension function for Figure
fun Figure.details(): String {
    return "Figure: $name, Area: ${area()}, Perimeter: ${perimeter()}"
}

fun taskIV() {
    println("=== Zadanie IV ===")

    val figures: List<Figure> = listOf(
        Square(5.0),
        Rectangle(4.0, 6.0),
        Circle(3.0)
    )

    for (fig in figures) {
        fig.describe()
        println("Area = ${fig.area()}")
        println("Perimeter = ${fig.perimeter()}")
        println("Details (extension): ${fig.details()}")
        println()
    }
}

/*===================*/
/*       TASK V      */
/*===================*/
data class Product(
    val name: String,
    val price: Double,
    val category: String
)

interface DiscountPolicy {
    fun calculateDiscountedPrice(product: Product): Double
}

class NoDiscount : DiscountPolicy {
    override fun calculateDiscountedPrice(product: Product): Double {
        return product.price
    }
}

class FixedAmountDiscount(private val discountValue: Double) : DiscountPolicy {
    override fun calculateDiscountedPrice(product: Product): Double {
        val discounted = product.price - discountValue
        return if (discounted < 0.0) 0.0 else discounted
    }
}

class Cart<T> {
    private val items = mutableListOf<T>()

    fun addItem(item: T) {
        items.add(item)
    }

    fun removeItem(item: T) {
        items.remove(item)
    }

    fun getItems(): List<T> = items

    fun sortByPrice(selector: (T) -> Double) {
        items.sortBy(selector)
    }
}

fun calculateCartTotal(cart: Cart<Product>, policy: DiscountPolicy): Double {
    var total = 0.0
    for (product in cart.getItems()) {
        total += policy.calculateDiscountedPrice(product)
    }
    return total
}

fun taskV() {
    println("=== Zadanie V ===")

    val cart = Cart<Product>()
    cart.addItem(Product("Book", 30.0, "Education"))
    cart.addItem(Product("Headphones", 100.0, "Electronics"))
    cart.addItem(Product("Cup", 10.0, "Home"))

    val noDiscountPolicy = NoDiscount()
    val fixedDiscountPolicy = FixedAmountDiscount(15.0)

    println("Total with NoDiscount: ${calculateCartTotal(cart, noDiscountPolicy)}")
    println("Total with FixedAmountDiscount(15.0): ${calculateCartTotal(cart, fixedDiscountPolicy)}")

    cart.sortByPrice { it.price }
    println("Products after sorting by price:")
    for (product in cart.getItems()) {
        println(product)
    }
}

/*===================*/
/*      TASK VI      */
/*===================*/
object SystemLog {
    private val logs = mutableListOf<String>()

    fun writeLog(message: String) {
        logs.add(message)
    }

    fun printLogs() {
        for (log in logs) {
            println(log)
        }
    }
}

class User(val firstName: String, val lastName: String) {
    var age: Int = 0

    // Secondary constructor
    constructor(firstName: String, lastName: String, age: Int) : this(firstName, lastName) {
        this.age = age
        SystemLog.writeLog("Created user with age = $age")
    }

    companion object {
        var numberOfUsers = 0

        fun create(firstName: String, lastName: String): User {
            numberOfUsers++
            SystemLog.writeLog("User created via companion object.")
            return User(firstName, lastName)
        }
    }
}

fun taskVI() {
    println("=== Zadanie XI ===")

    val userA = User("John", "Doe") // Primary
    SystemLog.writeLog("Created user (primary)")

    val userB = User("Alice", "Johnson", 25) // Secondary

    val userC = User.create("Bob", "Smith") // Companion object

    SystemLog.writeLog("Number of users so far: ${User.numberOfUsers}")

    SystemLog.printLogs()
}

/*===================*/
/*      TASK VII     */
/*===================*/
open class SystemResource(val name: String, var sizeMB: Int) {
    open fun info() {
        println("Resource: $name, Size: $sizeMB MB")
    }

    open fun totalSizeMB(): Int {
        return sizeMB
    }
}

class FileResource(name: String, sizeMB: Int, val fileType: String) : SystemResource(name, sizeMB) {
    override fun info() {
        println("File: $name, Type: $fileType, Size: $sizeMB MB")
    }
}

class FolderResource(name: String, sizeMB: Int) : SystemResource(name, sizeMB) {
    val contents = mutableListOf<SystemResource>()

    fun addResource(resource: SystemResource) {
        contents.add(resource)
    }

    override fun info() {
        println("Folder: $name, Contains: ${contents.size} resources.")
    }

    operator fun plus(other: SystemResource): FolderResource {
        contents.add(other)
        return this
    }

    override fun totalSizeMB(): Int {
        var totalSize = sizeMB
        for (res in contents) {
            totalSize += res.totalSizeMB()
        }
        return totalSize
    }
}

fun taskVII() {
    println("=== Zadanie VII ===")

    val rootFolder = FolderResource("RootFolder", 2)
    val file1 = FileResource("Document.txt", 5, "txt")
    val file2 = FileResource("Photo.jpg", 10, "jpg")
    val subFolder = FolderResource("SubFolder", 1)
    val file3 = FileResource("Presentation.ppt", 15, "ppt")

    rootFolder.addResource(file1)
    rootFolder + file2 + subFolder
    subFolder.addResource(file3)
    rootFolder.info()
    println("Total size (recursive) in ${rootFolder.name} = ${rootFolder.totalSizeMB()} MB")


    for (res in rootFolder.contents) {
        res.info()
        println("-> Total size (recursive) for ${res.name} = ${res.totalSizeMB()} MB")
    }
}

fun main() {
    taskI()
    taskII()
    taskIII()
    taskIV()
    taskV()
    taskVI()
    taskVII()
}
