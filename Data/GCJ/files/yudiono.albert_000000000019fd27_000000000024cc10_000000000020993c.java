@file:JvmName("Solution")

fun main() {
    val tc = readLine()!!.toInt()
    for (t in 1..tc) {
        val n = readLine()!!.toInt()
        val matrix = mutableListOf<List<Int>>()
        for (i in 0 until n) {
            matrix.add(readLine()!!.split(" ").map { it.toInt() })
        }

        var k = 0
        var r = 0
        var c = 0
        for (i in 0 until n) {
            var isSameRow = false
            var isSameCol = false
            val setRow = mutableSetOf<Int>()
            val setCol = mutableSetOf<Int>()
            for (j in 0 until n) {
                if (i == j) k += matrix[i][j]
                if (!setRow.add(matrix[i][j])) isSameRow = true
                if (!setCol.add(matrix[j][i])) isSameCol = true
            }
            if (isSameRow) r += 1
            if (isSameCol) c += 1
        }

        println("#Case #$t: $k $r $c")
    }
}