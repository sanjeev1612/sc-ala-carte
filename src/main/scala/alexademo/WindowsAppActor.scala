package alexademo


import akka.actor.{Actor, ActorLogging}

class WindowsAppActor extends Actor with ActorLogging {

  val brain = context.actorSelection("/user/Brain")

  val windowAppRespondsto = List(
    "notepad", "winword", "calc", "cmd"
  )

  def receive = {

    case WillYouHandlePhrase(s) =>
      val weWillHandle = Utils.matchesARegex(windowAppRespondsto, s)
      sender ! weWillHandle
      if (weWillHandle) {
        log.info(s"TimeActor will handle '$s'")
        handleCommand(s)
      }

    case WhatPhrasesCanYouHandle =>
      sender ! windowAppRespondsto

    case unknown =>
      log.info("BrainMessageProcessor got an unknown message: " + unknown)

  }

  private def handleCommand(s:String) {
    brain ! SpeakText(handleWindowApp(s))

  }

  private def isAPhraseWeUnderstand(phrase: String): Boolean = {
    for (s <- windowAppRespondsto) {
      if (phrase.matches(s)) return true;
    }
    false
  }

  private def handleWindowApp(app: String): String = {

    val rs = Runtime.getRuntime();
    rs.exec(app)
    s"opening ${app} ......"
  }

}




