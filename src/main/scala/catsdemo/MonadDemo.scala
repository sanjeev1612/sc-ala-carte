package catsdemo

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FutureMonad extends App {
  def doSomethingLongRunning: Future[Int] = ???

  def doSomethingElseLongRunning: Future[Int] = ???

  def doSomethingVeryLongRunning: Future[Int] =
    for {
      result1 <- doSomethingLongRunning
      result2 <- doSomethingElseLongRunning
    } yield result1 + result2

  def doSomethingVeryLongRunning2: Future[Int] =
    doSomethingLongRunning.flatMap { result1 =>
      doSomethingElseLongRunning.map { result2 =>
        result1 + result2
      }
    }

}