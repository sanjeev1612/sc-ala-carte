package cats

//RULE # Associative (order of operation doesn't matter ) Also, has an identity element

trait MySemigroup[A] {
  def combine(x: A, y: A): A
}

trait MyMonoid[A] extends MySemigroup[A] {
  def empty: A
}

object test2 extends App {

  import cats.Monoid
  import cats.instances.string._ // for Monoid
  Monoid[String].combine("Hi ", "there")
  // res0: String = Hi there
  Monoid[String].empty
  // res1: String = ""

  import cats.instances.string._ // for Monoid
  import cats.syntax.semigroup._ // for |+|
  val stringResult = "Hi " |+| "there" |+| Monoid[String].empty
  // stringResult: String = Hi there

  import cats.instances.int._ // for int  Monoid
  import cats.instances.option._
  val intResult = 1 |+| 2 |+| Monoid[Int].empty //3
  val optionResult= Option(12) |+| Option(20)
}
