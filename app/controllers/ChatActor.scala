package controllers

import akka.actor.{Actor, ActorRef, Props}
import controllers.ChatActor.SendMessage
import play.api.libs.json._
import play.api.libs.json.JsValue
//clientActor is provided by Play and is used ti communicate with the client( curl or a browser)
class ChatActor(clientActor:ActorRef,manager:ActorRef) extends Actor{
  import controllers.ChatManager._

  manager ! NewChatter(self)
  val logger=play.api.Logger(this.getClass)
  import ChatActor.jsonToString
  override def receive: Receive = {
    case jsonMessage:JsValue=>
      manager ! Message(jsonMessage)
      logger.info("Connected to browser")
      logger.info("Sent message to  Manager")
    case SendMessage(jsMessage)=>
      val stringMessage=jsonToString(jsMessage)
      val sendMessage:JsValue=Json.parse(s""" {"body" : "Chatter :  '$stringMessage'"}""")
      clientActor! sendMessage
      logger.info("replied Client with JSON data")

  }
}
object ChatActor{
  case class SendMessage(msg:JsValue)
  private[controllers]def props(clientActor:ActorRef,manager:ActorRef):Props=Props(new ChatActor(clientActor,manager))
  private[controllers] def jsonToString(json:JsValue)=(json \ "key").as[String]
}
