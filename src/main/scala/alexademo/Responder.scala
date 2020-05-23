package alexademo

import akka.actor.{Actor, ActorLogging, Props}

class Responder extends Actor with ActorLogging {

    val mouth = context.actorSelection("/user/Mouth")

    val brainMessageProcessor = context.actorOf(Props[BrainMessageProcessor])

    def receive = {

        case MessageFromEars(msg) =>
            log.info(s"Brain got a MessageFromEars ($msg)")
            brainMessageProcessor ! MessageFromEars(msg)

        case SpeakText(msg) =>
            log.info(s"Brain got a SpeakText message ($msg)")
            mouth ! SpeakText(msg)
            
        case SpeakList(list) =>
            log.info(s"Brain got a SpeakList message")
            mouth ! SpeakList(list)

        case unknown =>
            log.info("Brain got an unknown message: " + unknown)

    }    

}

