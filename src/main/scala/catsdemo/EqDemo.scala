package catsdemo

import cats.syntax.eq._
import cats.instances.int._
import cats.instances.option._
import cats.kernel.Eq
import cats.syntax.option._


case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catEquality = Eq.instance[Cat] { (cat1, cat2) => cat1.name == cat2.name }

}

object EqDemo extends App {
  // integer Comparison using Eq
  println(1 === 11)

  //option comparison using Eq
  println(1.some === none[Int])

  //negative comparison
  1.some =!= none[Int]

  import java.util.Date
  import cats.instances.long._ // for Eq
  implicit val dateEq: Eq[Date] =
    Eq.instance[Date] { (date1, date2) =>
      date1.getTime === date2.getTime
    }
  val x = new Date() // now
  val y = new Date() // a bit later than now
  println(x === x)
  // res13: Boolean = true
  println(x === y)
  // res14: Boolean = false

  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Garfield", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  println("cat comparison " + (cat1 === cat2))

  println("option of cat comparison " + (cat1.some === cat2.some))


}
