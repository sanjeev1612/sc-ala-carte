package alexa


case object Hello
case object SayHello
case object ShowPrompt
case class MessageFromEars(s: String)

case class SpeakText(text: String)
case class SpeakList(list: Seq[String])

case class WillYouHandlePhrase(s: String)

case object WhatPhrasesCanYouHandle
case class PhrasesICanHandle(xs: Seq[String])
