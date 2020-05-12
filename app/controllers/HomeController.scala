package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject._
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit system:ActorSystem,mat:Materializer) extends AbstractController(cc) {

val logger=play.api.Logger(this.getClass)//val logger=play.api.Logger(getClass)
  val manager=system.actorOf(ChatManager.props,"Manager")

  def index = Action {implicit requestHeader:RequestHeader=>
    logger.info("index page")
    Ok(views.html.index())
  }
  def socket: WebSocket =WebSocket.accept[JsValue,JsValue]{ request=>
    //a normal request is converted into a websocket
    logger.info("Websocket called")
    ActorFlow.actorRef{client=>
      ChatActor.props(client,manager)

    }
  }
  /*
  This is the socket for dealing with String messages and replies as Strings
  def socket: WebSocket =WebSocket.accept[String,String]{ request=>
    //a normal request is converted into a websocket
    logger.info("Websocket called")
    ActorFlow.actorRef{client=>
      ChatActor.props(client)

    }
  }
   */
}
