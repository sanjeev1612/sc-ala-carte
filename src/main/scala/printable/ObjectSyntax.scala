package printable

sealed trait Json

final case class JsString(value: String) extends Json

final case class JsInteger(value: Int) extends Json

final case class JsDouble(value: Double) extends Json

final case class JsObject(get: Map[String, Json]) extends Json

case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json

}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }
  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
    new JsonWriter[Option[A]] {
      def write(option: Option[A]): Json =
        option match {
          case Some(aValue) => writer.write(aValue)
          case None => JsNull
        }
    }
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

object TestApp extends App {

  import JsonWriterInstances._

  println(Json.toJson(Person("Dave", "dave@example.com")))

}
