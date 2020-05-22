package alexa

import akka.actor.{Actor, ActorLogging}

class VoiceController extends Actor with ActorLogging {

    def receive = {

        case SayHello =>
            println("Hello back at you.")

        case SpeakText(text) =>
            println(text)

        case SpeakList(list) =>
            //list.foreach(println)
            for ((s, i) <- list.zip(Stream from 1)) {
                println(s"$i. $s")
            }


            
        case unknown =>
            println(s"Mouth: I don't know what that ($unknown) was.")

    }

}


