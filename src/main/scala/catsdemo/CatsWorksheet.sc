import cats.Functor
import cats.instances.option._

import scala.language.higherKinds

val func = (x: Int) => x + 1
val liftedFunc: Option[Int] => Option[Int] = Functor[Option].lift(func)
liftedFunc(Option(1))

import cats.syntax.functor._

def doMath[F[_]](start: F[Int])
                (implicit functor: Functor[F]): F[Int] =
  start.map(n => n + 1 * 2)

import cats.instances.option._
import cats.instances.list._

doMath(Option(20))
doMath(List(1, 2, 3))

for {
  x <- (1 to 3).toList
  y <- (4 to 5).toList
} yield (x, y)

val list1 = (1 to 3).toList
val list2 = (4 to 5).toList

// break of for comprehension in flatmap and map
list1.flatMap(a => list2.map(b => (a, b)))

import cats.Monad
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap
import scala.language.higherKinds

def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x * x + y * y))

import cats.instances.option._ // for Monad
import cats.instances.list._ // for Monad
sumSquare(Option(3), Option(4))
sumSquare(List(1, 2, 3), List(4, 5))

import cats.data.Writer
import cats.instances.vector._ // for Monoid
Writer(Vector(
  "It was the best of times",
  "it was the worst of times"
), 1859)

def demoImplicits(param: String = "Hello")(implicit name: String): String = {
  s"${param} your implicit parameter is ${name}"
}
implicit val str="implicit value"
demoImplicits()