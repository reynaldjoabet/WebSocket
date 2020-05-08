package controllers

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json._

import play.api.libs.json.JsValue
//clientActor is provided by Play and is used ti communicate with the client( curl or a browser)
class ChatActor(clientActor:ActorRef) extends Actor{
  val logger=play.api.Logger(this.getClass)
  import ChatActor.jsonToString
  override def receive: Receive = {

    case jsonMessage:JsValue=>
      logger.info("Connected to client/browser")
      val stringMessage=jsonToString(jsonMessage)
      val sendMessage:JsValue=Json.parse(s""" {"body" : "Browser :  '$stringMessage'"}""")
      clientActor! sendMessage
      logger.info("replied Client with JSON data")
  }
}
object ChatActor{
  private[controllers]def props(clientActor:ActorRef):Props=Props(new ChatActor(clientActor))
  private[controllers] def jsonToString(json:JsValue)=(json \ "key").as[String]
}
