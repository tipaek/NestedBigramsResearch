
import java.util.Scanner
import kotlin.math.abs

fun main(args: Array<String>) {
  val scanner = Scanner(System.`in`)
  val T = scanner.nextInt()

  for (t in 1..T) {
    var X = scanner.nextInt()
    var Y = scanner.nextInt()
    val M = scanner.nextLine().trim()

    var impossible = true
    for (i in 1..M.length) {
      when(M[i-1]) {
        'N' -> Y++
        'S' -> Y--
        'E' -> X++
        'W' -> X--
        else -> {}
      }
      if ((abs(X) + abs(Y)) <= i) {
        println("Case #$t: $i")
        impossible = false
        break
      }
    }
    if(impossible) {
      println("Case #$t: IMPOSSIBLE")
    }
  }

}