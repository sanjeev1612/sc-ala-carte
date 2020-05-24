package catsdemo

import scala.language.higherKinds

trait MyFunctor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object DemoFunctor extends App {

  import scala.language.higherKinds
  import cats.Functor
  import cats.instances.list._
  import cats.instances.option._

  val list1 = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2)
  val option1 = Option(123)
  val option2 = Functor[Option].map(option1)(_.toString)
  println(list2)
  println(option2)
}