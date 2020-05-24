package catsdemo

import cats.data.Writer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}

object WriterDemo extends App {

  import cats.syntax.writer._ // for writer
  val a = Writer(Vector("msg1", "msg2", "msg3"), 123)
  // a: cats.data.WriterT[cats.Id,scala.collection.immutable.Vector[String],Int] = WriterT((Vector(msg1, msg2, msg3),123))
  val b = 123.writer(Vector("msg1", "msg2", "msg3"))
  // b: cats.data.Writer[scala.collection.immutable.Vector[String],Int] = WriterT((Vector(msg1, msg2, msg3),123))

  val aResult: Int = a.value
  // aResult: Int = 123
  val aLog: Vector[String] = a.written
  // aLog: Vector[String] = Vector(msg1, msg2, msg3)

  val (log, result) = b.run
  // log: scala.collection.immutable.Vector[String] = Vector(msg1, msg2,  msg3)
  // result: Int = 123

  import cats.syntax.applicative._
  import cats.instances.vector._

  val writer1 = for {
    a <- 10.pure[Logged]
    _ <- Vector("a", "b", "c").tell
    b <- 32.writer(Vector("x", "y", "z"))
  } yield a + b
  // writer1: cats.data.WriterT[cats.Id,Vector[String],Int] = WriterT((Vector(a, b, c, x, y, z),42))
  writer1.run
  // res4: cats.Id[(Vector[String], Int)] = (Vector(a, b, c, x, y, z) ,42)
  val writer2 = writer1.mapWritten(_.map(_.toUpperCase))
  // writer2: cats.data.WriterT[cats.Id,scala.collection.immutable. Vector[String],Int] = WriterT((Vector(A, B, C, X, Y, Z),42))
  writer2.run
  // res5: cats.Id[(scala.collection.immutable.Vector[String], Int)] = (Vector(A, B, C, X, Y, Z),42)

  val writer3 = writer1.bimap(log => log.map(_.toUpperCase), res => res * 100)
  // writer3: cats.data.WriterT[cats.Id,scala.collection.immutable.Vector[String],Int] = WriterT((Vector(A, B, C, X, Y, Z),4200))
  writer3.run
  // res6: cats.Id[(scala.collection.immutable.Vector[String], Int)] = (Vector(A, B, C, X, Y, Z),4200)
  val writer4 = writer1.mapBoth { (log, res) =>
    val log2 = log.map(_ + "!")
    val res2 = res * 1000
    (log2, res2)
  }
  // writer4: cats.data.WriterT[cats.Id,scala.collection.immutable. Vector[String],Int] = WriterT((Vector(a!, b!, c!, x!, y!, z!),42000))
  writer4.run
  // res7: cats.Id[(scala.collection.immutable.Vector[String], Int)] = ( Vector(a!, b!, c!, x!, y!, z!),42000)
  val writer5 = writer1.reset
  // writer5: cats.data.WriterT[cats.Id,Vector[String],Int] = WriterT(( Vector(),42))
  writer5.run
  // res8: cats.Id[(Vector[String], Int)] = (Vector(),42)
  val writer6 = writer1.swap
  // writer6: cats.data.WriterT[cats.Id,Int,Vector[String]] = WriterT ((42,Vector(a, b, c, x, y, z)))
  writer6.run
  // res9: cats.Id[(Int, Vector[String])] = (42,Vector(a, b, c, x, y, z)  )


  //==================================================================================================================================
  //Example for created logged factorial

  import cats.data.Writer
  import cats.syntax.applicative._ // for pure
  type Logged[A] = Writer[Vector[String], A]
  42.pure[Logged]
  // res13: Logged[Int] = WriterT((Vector(),42))

  import cats.syntax.writer._ // for tell
  Vector("Message").tell
  // res14: cats.data.Writer[scala.collection.immutable.Vector[String], Unit] = WriterT((Vector(Message),()))

  import cats.instances.vector._ // for Monoid
  41.pure[Logged].map(_ + 1)

  // res15: cats.data.WriterT[cats.Id,Vector[String],Int] = WriterT((Vector(), 42 ) )
  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)


  def factorial(n: Int): Logged[Int] =
    for {
      ans <- if (n == 0) {
        1.pure[Logged]
      } else {
        slowly(factorial(n - 1).map(_ * n))
      }
      _ <- Vector(s"fact $n $ans").tell
    } yield ans

  val (log1, res) = factorial(5).run
  // log: Vector[String] = Vector(fact 0 1, fact 1 1, fact 2 2, fact 3  6, fact 4 24, fact 5 120)
  // res: Int = 120

  import scala.concurrent.duration._

  val Vector((logA, ansA), (logB, ansB)) = Await.result(Future.sequence(Vector(Future(factorial(3).run), Future(factorial(5).run)
  )), 5.seconds)
  // logA: Vector[String] = Vector(fact 0 1, fact 1 1, fact 2 2, fact 3  6   )
  // ansA: Int = 6
  // logB: Vector[String] = Vector(fact 0 1, fact 1 1, fact 2 2, fact 3  6, fact 4 24, fact 5 120)
  // ansB: Int = 120
}
