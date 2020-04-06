package helloWorld

object TwoWaysOfPgming extends App {

  // What to do
  def sumByFP(xs: List[Int]): Long = xs match {
    case Nil => 0
    case head :: tail => head + sumByFP(tail)
  }

  println(s"${sumByFP((1 to 10).toList)} is sum returned by Functional Call ")



  // How to do step by step
  def sumByImperative(xs: List[Int]): Long = {
    var sum = 0
    for (i <- xs) {
      sum = sum + i
    }

    sum
  }

  println(s"${sumByImperative((1 to 10).toList)} is sum returned by Imperative Call ")
}
