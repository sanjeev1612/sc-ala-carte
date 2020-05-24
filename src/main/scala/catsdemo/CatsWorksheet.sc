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


