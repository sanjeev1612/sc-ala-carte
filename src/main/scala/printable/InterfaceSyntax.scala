package printable

object JsonSyntax {

  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }

}

object tester2 extends App {

  import JsonSyntax._
  import JsonWriterInstances._

  val impPerson = implicitly[JsonWriter[Person]]
  println(impPerson)
  println(Option(Person("Dave", "dave@example.com")).toJson)
}