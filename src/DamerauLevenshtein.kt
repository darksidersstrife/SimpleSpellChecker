class DamerauLevenshteinDistance(
    val insertCost: Double = 1.0,
    val deleteCost: Double = 1.0,
    val substitutionCost: Double = 1.0,
    val transposeCost: Double = 1.0
) {

    fun compute(_firstString: String, _secondString: String): Double {
        val firstString: String
        val secondString: String
        val insertCost: Double
        val deleteCost: Double

        if (_firstString.length < _secondString.length) {
            firstString = _firstString
            secondString = _secondString
            insertCost = this.insertCost
            deleteCost = this.deleteCost
        } else {
            firstString = _secondString
            secondString = _firstString
            insertCost = this.deleteCost
            deleteCost = this.insertCost
        }

        val prevDistances = Array(firstString.length + 1) { it.toDouble() }
        val distances = Array(firstString.length + 1) { 1.0 }

        for (i in 1..firstString.length) {
            val same = if (firstString[i - 1] == secondString[0]) 0.0 else 1.0
            distances[i] = minOf(
                distances[i - 1] + deleteCost,
                prevDistances[i] + insertCost,
                prevDistances[i - 1] + same
            )
        }

        for (j in 2..secondString.length) {
            var prevPrev = prevDistances[0]
            prevDistances[0] = distances[0]
            distances[0] = j.toDouble()

            var prev = prevDistances[1]
            prevDistances[1] = distances[1]

            val same = if (firstString[0] == secondString[j - 1]) 0.0 else 1.0
            distances[1] = minOf(
                distances[0] + deleteCost,
                distances[1] + insertCost,
                prevDistances[0] + same * substitutionCost
            )

            for (i in 2..firstString.length) {
                val cur = distances[i]

                val same = if (firstString[i - 1] == secondString[j - 1]) 0.0 else 1.0
                distances[i] = minOf(
                    distances[i - 1] + deleteCost,
                    distances[i] + insertCost,
                    prevDistances[i - 1] + same * substitutionCost
                )

                if (firstString[i - 2] == secondString[j - 1] && firstString[i - 1] == secondString[j - 2]) {
                    distances[i] = minOf(
                        distances[i],
                        prevPrev + same * transposeCost
                    )
                }

                prevPrev = prev
                prev = prevDistances[i]
                prevDistances[i] = cur
            }
        }
        return distances.last()
    }
}