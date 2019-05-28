import java.io.File

fun main(args: Array<String>) {
    val dlDistance = DamerauLevenshteinDistance()
    val dictionary = File("dictionary.txt").readLines()


    var bestScore = Double.POSITIVE_INFINITY
    var bestString = ""

    val str = readLine()
    while (str != null) {
        for (word in dictionary) {
            val newScore = dlDistance.compute(str, word)

            if (newScore < bestScore) {
                bestScore = newScore
                bestString = word

                if (newScore == 0.0)
                    break
            }
        }
        if (bestScore == 0.0) {
            println("Correct")
        } else {
            println("Incorrect, did you mean $bestString?")
        }
    }
}