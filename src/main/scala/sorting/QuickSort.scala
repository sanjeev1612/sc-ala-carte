package sorting


object QuickSort extends App {
  println(quicksort(List(6, 9, 23, 1, 2, 3)))

  def quicksort(xs: List[Int]): List[Int] = {
    xs match {
      case Nil => Nil
      case head :: tail => {
        val (low, high) = tail.partition(_ < head);
        quicksort(low) ::: head :: quicksort(high)
      }
    }
  }

}
