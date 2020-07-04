import scala.annotation.tailrec

val m1 = Map("a" -> 1, "b" -> 3, "c" -> 4)
val m2 = Map("d" -> 1, "b" -> 2, "c" -> 2)
val m3 = m1.toList
val m4 = m2.toList
val m5 = m3 ++ m4
val result = m5.groupBy(_._1)
val result1 = m5.groupBy(_._1).map(a => (a._1, a._2.map(a => a._2).sum))

def factorial(n: Int): Int = {
  @tailrec
  def iter(x: Int, result: Int): Int =
    if (x ==  0) result
    else iter(x - 1, result * x)

  iter(n,1)
}
factorial(5)