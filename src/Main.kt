import java.util.*

fun main() {
    
}

fun task1() {
    val intVal: Int = 10
    val doubleVal: Double = 20.5
    val floatVal: Float = 10.0f
    val longVal: Long = 100L
    val shortVal: Short = 5
    val byteVal: Byte = 1
    val booleanVal: Boolean = true
    val charVal: Char = 'A'
    val stringVal: String = "Hello Kotlin"

    val doubleFromInt: Double = intVal.toDouble()
    println(doubleFromInt)
}

//const val FORBIDDEN = 0
//const val EXECUTE = 1
//const val WRITE = 2
//const val READ = 4
//
//fun checkPermission(perms: Int): Boolean {
//    val hasRead = (perms and READ) == READ
//    val hasExecute = (perms and EXECUTE) == EXECUTE
//    return hasRead and hasExecute
//}
//
//fun task2() {
//    println("checkPermissions(5) = ${checkPermission(5)}")
//    println("checkPermissions(7) = ${checkPermission(7)}")
//    println("checkPermissions(4) = ${checkPermission(4)}")
//    println("checkPermissions(1) = ${checkPermission(1)}")
//    println("checkPermissions(6) = ${checkPermission(6)}")
//}


fun task3() {
    var original = "kot"
    var refs = original

    println(refs == original)
    println(refs === original)

    print("nowa wartosc dla refs")
    var scanner = Scanner(System.`in`)
    refs = scanner.next()

    println(refs == original)
    println(refs === original)
}

fun task4() {
    print("nowa linia tekstu")
    var scanner = Scanner(System.`in`)
    var text = scanner.nextLine()
    println(text)

    if(text.isBlank()) {
        println("pusty")
    } else {
        text
            .split(" ")
            .map { tmp ->
                tmp.filter { it.isLetterOrDigit() }
            }
            .filter { tmp2 ->
                tmp2.isNotEmpty() }
            .map { tmp2 -> tmp2.reversed()}
            .forEach { println(it)
        }
    }
}

fun task5() {
    val tasksList = mutableListOf<String>()
    while (true) {
        println("1. Wyświetl zadania")
        println("2. Dodaj zadanie")
        println("3. Usuń zadanie")
        println("0. Wyjdź")
        println("Wybierz opcję: ")
        val scanner = Scanner(System.`in`)
        val option = scanner.nextInt()
        scanner.nextLine()
        when (option) {
            1 -> {
                println("Wyświetl zadania")
                for (task in tasksList) {
                    println(task)
                }
            }

            2 -> {
                println("Dodaj zadanie")
                val task = scanner.nextLine()
                if(task.isBlank()) {
                    println("Nie podano zadania")
                } else {
                    println("Dodano zadanie")
                    tasksList.add(task)
                }
            }

            3 -> {
                println("Usuń zadanie")
                val task = scanner.nextLine()
                if(task.isBlank()) {
                    println("Nie podano zadania")
                } else {
                    tasksList.remove(task)
                }
                for(tmp in tasksList) {
                    if(task.equals(tmp)) {
                        tasksList.remove(tmp)
                        println("Usunięto zadanie")
                    }
                    else {
                        println("Nie ma takiego zadania")
                    }
                }
            }

            0 -> {
                println("Wyjdź")
                break
            }

            else -> {
                println("Niepoprawna opcja")
            }
        }
    }
}

fun task6(){
    val monthsString = """
        Styczen, Luty, Marzec, Kwiecien, Maj, Czerwiec,
        |Lipiec, Sierpien, Wrzesien, Pazdziernik, Listopad,
        |Grudzien
    """.trimMargin()

    val months = monthsString.split(",").map { it.trim() }

    println("Wszystkie za pomocą ranges")
    for(i in 0..<months.size){
        println("${i + 1}. ${months[i]}")
    }

    println("Wszystkie na L")
    for(month in months){
        if(month.startsWith("L", ignoreCase = true)){
            println(month)
        }
    }

    println("Co drugi miesiąc za pomocą Indexed")
    months.forEachIndexed({index, month ->
        if(index % 2 == 0){
            println(month)
        }
    })

    println("While + iterator")
    val iterator = months.iterator()
    var counter = 1
    while(iterator.hasNext()){
        println("${counter++}. ${iterator.next()}")
    }

    println("Rekurencyjnie")
    recursivePrint(0, months)

    println("JoinToString")
    println(months.joinToString(" | "))

    println("Filtrowanie P i zamiana e na _")
    months.filterNot { it.startsWith("P", ignoreCase = true) }
        .forEach { month ->
            val replaced = month.replace('e', '_')
            println(replaced)
        }
}

fun recursivePrint(i: Int, months: List<String>){
    if(i >= months.size){
        return
    }
    println("${i + 1}. ${months[i]}")
    recursivePrint(i + 1, months)
}

fun task9 (vararg elements: String):Map<String, String>{
    val list = elements.toList()
    list.forEach { it.lowercase() }
    val answerMap = mutableMapOf<String, String>()
    for(key in answerMap) {
        val tmp = key.value
        when (tmp) {
            "luty", "lipiec", "sierpien", "wrzesien" -> {
                answerMap[key.value] = "Semestr letni"
            }

            "pazdziernik", "listopad", "grudzien", "styczen" -> {
                answerMap[key.value] = "Semestr zimowy"
            }

            "marzec", "kwiecien", "maj", "czerwiec" -> {
                answerMap[key.value] = "Semestr wiosenny"
            }

            else -> {
                println("Nie ma takiego miesiąca")
            }
        }
    }
    return answerMap
}

fun task10(start: Int, end: Int, step: Int): List<Int> {
    val list = mutableListOf<Int>()
    for(i in start..end step step){
        list.add(i)
    }
    return list
}

fun task11(data: List<String?>): List<String> {
    val list = mutableListOf<String>()
    for(i in data){
        if(i != null){
            list.add(i)
        }
    }
    return list
}

fun normalize(data: List<String?>): List<String> {
    val list = mutableListOf<String>()
    for(i in data){
        if(i != null){
            list.add(i)
        }
    }
    list.forEach { it.trim().uppercase() }
    return list
}