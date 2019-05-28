import java.io.File

fun main(args: Array<String>) {
    val dlDistance = DamerauLevenshteinDistance()
    val dictionary = File("words.txt").readLines()


    var bestScore: Double
    var bestString: String

    var str = readLine()
    while (str != null) {
        bestScore = Double.POSITIVE_INFINITY
        bestString = ""
        for (word in dictionary) {
            if (word == "")
                continue
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
        str = readLine()
    }
}