package alexa

import java.util.Scanner

import akka.actor.{ActorSystem, Props}

object CLAlexa extends App {

  val system = ActorSystem("AlekaSystem")
  val goodbyeActor = system.actorOf(Props[GoodbyeActor], name = "GoodbyeActor")
  val helloActor = system.actorOf(Props[SayHelloActor], name = "HelloActor")
  val toDoListActor = system.actorOf(Props[ToDoListActor], name = "ToDoListActor")
  val timeActor = system.actorOf(Props[TimeActor], name = "TimeActor")
  val windowAppActor = system.actorOf(Props[WindowsAppActor], name = "WindowsAppActor")


  val ears = system.actorOf(Props[Listener], name = "Ears")
  val mouth = system.actorOf(Props[VoiceController], name = "Mouth")
  val brain = system.actorOf(Props[Responder], name = "Brain")

  val scanner = new Scanner(System.in)
  while (true) {
    Utils.showPrompt
    val input = scanner.nextLine
    ears ! input
  }


}