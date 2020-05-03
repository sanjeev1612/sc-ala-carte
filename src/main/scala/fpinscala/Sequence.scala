package fpinscala

import scala.collection.mutable.ArrayBuffer

case class Sequence[A](initialElements: A*) {

  val elems = new ArrayBuffer[A]()
  elems ++= initialElements

  def withFilter(p: A => Boolean): Sequence[A] = {
    val temBuffer = elems.filter(p)
    Sequence(temBuffer: _*)
  }

  def map[B](f: A => B): Sequence[B] = {
    val abMap = elems.map(f)
    Sequence(abMap: _*)
  }

  def foreach(block: A => Unit): Unit = {
    elems.foreach(block)
  }

  def flattenLike[B](seqOfSeq: Sequence[Sequence[B]]): Sequence[B] = {
    var xs = ArrayBuffer[B]()
    for (listB: Sequence[B] <- seqOfSeq) {
      for (e <- listB) {
        xs += e
      }
    }
    Sequence(xs: _*)
  }

  def flatMap[B](f: A => Sequence[B]): Sequence[B] = {
    val mapRes: Sequence[Sequence[B]] = map(f) //map
    flattenLike(mapRes) //flatten
  }

}
