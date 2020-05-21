package alexa

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.actor.{Actor, ActorLogging}

class TimeActor extends Actor with ActorLogging {

  val brain = context.actorSelection("/user/Brain")

  val timeRgexRespondsTo = List(
    "time", "now", "date", "time.*"
  )

  def receive = {

    case WillYouHandlePhrase(s) =>
      val weWillHandle = Utils.matchesARegex(timeRgexRespondsTo, s)
      sender ! weWillHandle
      if (weWillHandle) {
        log.info(s"TimeActor will handle '$s'")
        handleTime
      }

    case WhatPhrasesCanYouHandle =>
      sender ! timeRgexRespondsTo

    case unknown =>
      log.info("BrainMessageProcessor got an unknown message: " + unknown)

  }

  private def handleTime {
    brain ! SpeakText(getTime)

  }

  private def isAPhraseWeUnderstand(phrase: String): Boolean = {
    for (s <- timeRgexRespondsTo) {
      if (phrase.matches(s)) return true;
    }
    false
  }

  private def getTime: String = {

    val dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    val now = LocalDateTime.now();
    dtf.format(now).toString
  }

}




