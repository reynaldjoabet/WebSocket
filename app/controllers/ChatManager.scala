package controllers

import akka.actor.{Actor, ActorRef, Props}

import play.api.libs.json.JsValue


class ChatManager extends Actor{
  import controllers.ChatManager._
  private var chatters=List.empty[ActorRef]
  override def receive: Receive = {
    case NewChatter(chatter)=>chatters::=chatter
    case Message(msg)=> chatters.foreach{actorRef=>
      import controllers.ChatActor.SendMessage
      actorRef ! SendMessage(msg)
    }
  }
}
object ChatManager{
  case class NewChatter(chatter:ActorRef)
  case class Message(msg:JsValue)
 def props:Props=Props(new ChatManager)
}