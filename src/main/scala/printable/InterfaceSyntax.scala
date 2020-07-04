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


  println(Option(Person("Dave", "dave@example.com")).toJson)
}