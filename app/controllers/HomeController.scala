package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.libs.streams.ActorFlow
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit system:ActorSystem) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {implicit requestHeader:RequestHeader=>
    Ok(views.html.index())
  }
  def socket: WebSocket =WebSocket.accept[String,String]{request=>   //a normal request is converted into a websocket
    ActorFlow.actorRef{client=>
      ChatActor.props(client)
    }
  }
}
