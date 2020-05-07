package controllers

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
//clientActor is provided by Play and is used ti communicate with the client( curl or a browser)
class ChatActor(clientActor:ActorRef) extends Actor{
  val logger=play.api.Logger(this.getClass)
  override def receive: Receive = {
    case ms: String=>
      logger.info("Connected to client")
      clientActor! s" Client said $ms"
    clientActor ! "Thank you for connecting"
      logger.info("replied Client")
  }
}
object ChatActor{
  def props(clientActor:ActorRef):Props=Props(new ChatActor(clientActor))
}
