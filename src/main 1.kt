import java.util.*

fun taskI() {
    println("=== Zadanie I ===")

    val intVal: Int = 10
    val doubleVal: Double = 20.5
    val floatVal: Float = 10.0f
    val longVal: Long = 100L
    val shortVal: Short = 5
    val byteVal: Byte = 1
    val booleanVal: Boolean = true
    val charVal: Char = 'A'
    val stringVal: String = "Hello Kotlin"

    // Konwersje (Kotlin nie ma implicit casting):
    val doubleFromInt: Double = intVal.toDouble()
    val longFromInt: Long = intVal.toLong()

    // Char <-- Int wymaga toChar():
    val charFromInt: Char = intVal.toChar()

    // String <-- Int wymaga toString():
    val stringFromInt: String = intVal.toString()

    println("doubleFromInt = $doubleFromInt")
    println("longFromInt = $longFromInt")
    println("charFromInt = $charFromInt")
    println("stringFromInt = $stringFromInt")
    println()
}

const val FORBIDDEN = 0
const val EXECUTE = 1
const val WRITE = 2
const val READ = 4

fun checkPermissions(perms: Int): Boolean {
    val hasRead = (perms and READ) == READ
    val hasExecute = (perms and EXECUTE) == EXECUTE
    return hasRead and hasExecute
}

fun taskII() {
    println("=== Zadanie II ===")
    println("checkPermissions(5) = ${checkPermissions(5)}")
    println("checkPermissions(7) = ${checkPermissions(7)}")
    println("checkPermissions(4) = ${checkPermissions(4)}")
    println("checkPermissions(1) = ${checkPermissions(1)}")
    println("checkPermissions(6) = ${checkPermissions(6)}")
    println()
}

fun taskIII() {
    println("=== Zadanie III ===")
    val original = "kot"
    var refs = original

    println("refs == original? ${refs == original}")
    println("refs === original? ${refs === original}")

    print("Podaj nowe słowo dla refs: ")
    val scanner = Scanner(System.`in`)
    val newWord = scanner.nextLine()

    refs = newWord

    println("refs == original? ${refs == original}")
    println("refs === original? ${refs === original}")
    println()
}

fun taskIV() {
    print("Podaj linię tekstu: ")
    val line = readLine()

    if (line.isNullOrEmpty()) {
        println("Nie podano żadnego słowa")
    } else {
        line
            .split(" ")
            .map { original ->
                original.filter { it.isLetterOrDigit() }
            }
            .filter { cleaned -> cleaned.isNotEmpty() }
            .map { cleaned ->
                cleaned.reversed()
            }
            .forEach { println(it) }
    }
}

fun taskV() {
    println("=== Zadanie V ===")
    val tasksList = mutableListOf<String>()

    do {
        println("--- MENU ---")
        println("1. Wyświetl zadania")
        println("2. Dodaj zadanie")
        println("3. Usuń zadanie")
        println("0. Wyjście")
        print("Wybierz opcję: ")

        val choice = readLine()
        when (choice) {
            "1" -> {
                println("Lista zadań:")
                if (tasksList.isEmpty()) {
                    println("(Brak zadań)")
                } else {
                    tasksList.forEachIndexed { index, task ->
                        println("${index + 1}. $task")
                    }
                }
            }

            "2" -> {
                print("Podaj treść zadania: ")
                val newTask = readLine()
                if (!newTask.isNullOrEmpty()) {
                    tasksList.add(newTask)
                    println("Zadanie dodane.")
                } else {
                    println("Nie dodano zadania (puste?).")
                }
            }

            "3" -> {
                print("Które zadanie usunąć (numer)? ")
                val input = readLine()
                val indexToRemove = input?.toIntOrNull()
                if (indexToRemove == null || indexToRemove !in 1..tasksList.size) {
                    println("Niepoprawny numer zadania.")
                } else {
                    tasksList.removeAt(indexToRemove - 1)
                    println("Zadanie usunięte.")
                }
            }

            "0" -> {
                println("Koniec programu.")
                break
            }

            else -> println("Nieznany wybór. Spróbuj ponownie.")
        }
    } while (true)

    println()
}

fun taskVI() {
    println("=== Zadanie VI ===")
    val monthsString = """
        Styczen, Luty, Marzec, Kwiecien, Maj, Czerwiec,
        |Lipiec, Sierpien, Wrzesien, Pazdziernik, Listopad,
        |Grudzien
    """.trimMargin()

    val months = monthsString.split(",").map { it.trim() }

    println("--> 1. Pętla for z range")
    for (i in 0 ..< months.size) {
        println("${i + 1}. ${months[i]}")
    }

    println("--> 2. Miesiące zaczynające się na 'L'")
    for (month in months) {
        if (month.startsWith("L", ignoreCase = true)) println(month)
    }

    println("--> 3. forEachIndexed - co drugi miesiąc")
    months.forEachIndexed { index, m ->
        if (index % 2 == 0) println("${index + 1}. $m")
    }

    println("--> 4. while + iterator")
    val iterator = months.iterator()
    var counter = 1
    while (iterator.hasNext()) {
        println("${counter++}. ${iterator.next()}")
    }

    println("--> 5. Rekurencyjnie:")
    fun printMonthsRec(i: Int) {
        if (i >= months.size) return
        println("${i + 1}. ${months[i]}")
        printMonthsRec(i + 1)
    }
    printMonthsRec(0)

    println("--> 6. joinToString()")
    println(months.joinToString(" | "))

    println("--> 7. forEach + filtrowanie 'P' i zamiana 'e' -> '_'")
    months
        .filterNot { it.startsWith("P", ignoreCase = true) }
        .forEach { m ->
            val replaced = m.replace('e', '_')
            println(replaced)
        }

    println()
}

fun taskVII() {
    println("=== Zadanie VII ===")
    val products = mapOf(
        "Chleb" to 4.0,
        "Masło" to 8.5,
        "Szynka" to 25.0,
        "Jajka" to 12.0,
        "Mleko" to 3.2
    )

    val discounted = products.mapValues { (_, price) -> price * 0.8 }

    println("Oryginalne ceny:")
    products.forEach { (product, price) ->
        println("$product = $price zł")
    }

    println(" Po 80% obniżce:")
    discounted.forEach { (product, price) ->
        println("$product = ${"%.2f".format(price)} zł")
    }
    println()
}

fun taskVIII() {
    println("=== Zadanie VIII ===")
    val peopleMap = mapOf(1 to "Ala", 2 to "Piotr", 3 to "Kasia")
    val maybeName = peopleMap[10]
    val length = maybeName?.length
    println("Długość (może być null): $length")
    val safeLength = maybeName?.length ?: 0
    println("Długość (bezpiecznie) = $safeLength")
    println()
}

fun checkSemesterOrHolidays(vararg months: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for (m in months) {
        val lower = m.lowercase()
        val value = when (lower) {
            "luty", "lipiec", "sierpień", "sierpien", "wrzesień", "wrzesien" -> "Ferie"
            "październik", "pazdziernik", "listopad", "grudzień", "grudzien", "styczeń", "styczen" -> "Semestr zimowy"
            "marzec", "kwiecień", "kwiecien", "maj", "czerwiec" -> "Semestr letni"
            else -> "Nie ma takiego miesiąca"
        }
        result[m] = value
    }
    return result
}

fun taskIX() {
    println("=== Zadanie IX ===")
    val multipleMonths = checkSemesterOrHolidays("luty", "Lipiec", "Listopad", "Marzec", "Kot", "wrzesien")
    for ((month, desc) in multipleMonths) {
        println("$month -> $desc")
    }
    println()
}

fun generateNumbers(start: Int, end: Int, step: Int = 1): List<Int> {
    if (start > end) return emptyList()
    val result = mutableListOf<Int>()
    var current = start
    while (current <= end) {
        result.add(current)
        current += step
    }
    return result
}

fun taskX() {
    println("=== Zadanie X ===")
    val list1 = generateNumbers(1, 30, 1)
    val list2 = generateNumbers(5, 50, 5)
    val list3 = generateNumbers(50, 40)

    println("Liczby od 1 do 30 podzielne przez 3:")
    list1.filter { it % 3 == 0 }.forEach { print("$it ") }
    println()

    println("Liczby od 5 do 50 (co 5), pomijanie gdy > 100:")
    for (n in list2) {
        if (n > 100) continue
        print("$n ")
    }
    println()

    println("Pusta lista (start > end): $list3")
    println()
}

fun normalize(data: List<String?>): List<String> {
    return data
        .filterNotNull()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.uppercase() }
}

fun taskXI() {
    println("=== Zadanie XI ===")
    val inputData = listOf("Ala", null, "", "Kotlin", null, "")
    val normalized = normalize(inputData)
    println("Po normalizacji: $normalized")
    println()
}

fun main() {
    taskI()
    taskII()
    taskIII()
    taskIV()
    taskV()
    taskVI()
    taskVII()
    taskVIII()
    taskIX()
    taskX()
    taskXI()
}
