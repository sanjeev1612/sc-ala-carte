package alexademo

import akka.actor.{Actor, ActorLogging}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * The Brain hands off messages to us that it receives from the Ears.
 * Our job is to process those messages.
 */
class BrainMessageProcessor extends Actor with ActorLogging {

    val mouth = context.actorSelection("/user/Mouth")

    val whatCanISayPhrases = List(
        "w",
        "w\\?",
        "what",
        "what\\?",
        "whatsay.*",
        "what can i say.*",
        "say what.*"
    )

    val goodbyeActor  = context.actorSelection("/user/GoodbyeActor")
    val helloActor    = context.actorSelection("/user/HelloActor")
    val toDoListActor = context.actorSelection("/user/ToDoListActor")
    val timeactor  = context.actorSelection("/user/TimeActor")
    val windowActor  = context.actorSelection("/user/WindowsAppActor")
    val workers = List(goodbyeActor, helloActor, toDoListActor, timeactor,windowActor)

    def receive = {
        case MessageFromEars(msg) =>
            processMessageFromEars(msg)
        case unknown =>
            log.info("BrainMessageProcessor got an unknown message: " + unknown)
    }

    private def processMessageFromEars(msg: String) {
        if (Utils.matchesARegex(whatCanISayPhrases, msg)) {
            handleWhatCanISayRequest
        } else {
            if (msg.trim != "") {
                val handled = tryLettingActorsHandleThePhrase(msg)
                if (!handled) {
                    mouth ! SpeakText(s"could not handle the phrase '$msg'")
                }
            }
        }
    }


    private def handleWhatCanISayRequest: Unit = {

        implicit val timeout = Timeout(2 seconds)
        val listOfListOfPhrases = for (w <- workers) yield {
            val future = w ? WhatPhrasesCanYouHandle
            val phrases = Await.result(future, timeout.duration).asInstanceOf[Seq[String]]
            phrases
        }
        val phrases = listOfListOfPhrases.flatten
        mouth ! SpeakList(phrases)
    }


    private def tryLettingActorsHandleThePhrase(msg: String): Boolean = {
        implicit val timeout = Timeout(2 seconds)
        for (w <- workers) {
            val future = w ? WillYouHandlePhrase(msg)
            val handled = Await.result(future, timeout.duration).asInstanceOf[Boolean]
            if (handled) {
                return true
            }
        }
        false
    }

}





