package alexademo

import akka.actor.{Actor, ActorLogging}

class GoodbyeActor extends Actor with ActorLogging {

    val brain = context.actorSelection("/user/Brain")

    val goodbyeRegexesWeRespondTo = List(
        "adios",
        "aloha",
        "bye",
        "exit",
        "goodbye",
        "hasta.*",
        "quit"
    )

    def receive = {
        
        case WillYouHandlePhrase(s) =>
            val weWillHandle = Utils.matchesARegex(goodbyeRegexesWeRespondTo, s)
            sender ! weWillHandle
            if (weWillHandle) {
                log.info(s"GoodbyeActor will handle '$s'")
                handleGoodbye
            }

        case WhatPhrasesCanYouHandle =>
            sender ! goodbyeRegexesWeRespondTo
            
        case unknown =>
            log.info("BrainMessageProcessor got an unknown message: " + unknown)

    }
    
    private def handleGoodbye {
        brain ! SpeakText(getRandomGoodbyePhrase)
        Thread.sleep(500)
        context.system.terminate
        System.exit(0)
    }

    private def isAPhraseWeUnderstand(phrase: String): Boolean = {
        for (s <- goodbyeRegexesWeRespondTo) {
            if (phrase.matches(s)) return true;
        }
        false
    }

    private def getRandomGoodbyePhrase: String = {
        val phrases = List(
            "Aloha",
            "Bye!",
            "Goodbye.",
            "Hasta luego.",
            "Arrivederci")
        Utils.getRandomElement(phrases)
    }

}




