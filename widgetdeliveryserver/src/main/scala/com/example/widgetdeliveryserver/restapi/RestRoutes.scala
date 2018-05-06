package com.example.widgetdeliveryserver.restapi

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import com.example.widgetdeliveryserver.actor.Description.WidgetDescription

import scala.concurrent.Future

class RestRoutes(actor:ActorSystem) {//extends WidgetDeliveryApi{
  val route: Route = {
    //createってつけずに、postされたらなんでも強制的に書き換えるようにしたらeditいらない・・・？
    pathPrefix(Segments){json =>
      post {
        complete(json.toString)
      }
    }
  }
}

//trait WidgetDeliveryApi {
//  import com.example.widgetdeliveryserver.actor.WidgetDelivery._
//  def createWidgetDeliveryActorRef:ActorRef
//  lazy val widgetDeliveryACtorRef: ActorRef = createWidgetDeliveryActorRef
//  def createWidget(widgetId:Int,adSlot:Int,recoSlot:Int): Future[EventResponce] =
//    widgetDeliveryACtorRef.ask(Widget(widgetId,adSlot,recoSlot)).mapTo[EventResponce]
//}