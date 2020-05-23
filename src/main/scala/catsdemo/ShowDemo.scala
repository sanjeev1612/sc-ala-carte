package catsdemo

import cats.Show
import cats.syntax.show._

case class Person(id: Int, name: String, age: Int)

object Person {
  implicit val showPerson: Show[Person] = new Show[Person] {
    override def show(t: Person): String = s"Name : ${t.name}   Id : ${t.id}   Age : ${t.age}"
  }
}

object Demo extends App {
  val per1 = Person(1, "Sanjeev", 29)
  println(per1.show)
}