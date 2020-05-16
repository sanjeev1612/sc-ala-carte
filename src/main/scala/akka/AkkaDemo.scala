package akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class WhoToGreet(who :String )

class Greetor extends Actor{
  def receive={
    case WhoToGreet(who) => println(s"hello $who")
  }
}

object AkkaDemo extends App {

  val system =ActorSystem("Actor-System")
  val greeter: ActorRef =system.actorOf(Props[Greetor],"greeter")
  greeter ! WhoToGreet("Sanjeev from Akka App")


}
