package interview

import scala.util.Random

object test {
  def mergeSort[T <% Ordered[T]](xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (_, Nil) => xs
      case (Nil, _) => ys
      case (x :: xs1, y :: ys1) =>
        if (x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(mergeSort(ys), mergeSort(zs))
    }
  }

  def main(args: Array[String]) {
    val list = Seq.fill(50)(Random.nextInt(500)).toList
    println(s"list without sorting is ... ${list}")
    println(s"list after sorting is ... ${mergeSort(list)}")
  }
}