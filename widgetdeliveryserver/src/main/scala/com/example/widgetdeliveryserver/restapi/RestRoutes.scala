package com.example.widgetdeliveryserver.restapi

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import com.example.widgetdeliveryserver.actor.WidgetDelivery._
import com.example.widgetdeliveryserver.actor.WidgetJsonProtocol._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait RestRoutes {//extends WidgetDeliveryApi{

  def widgetDeliveryServerActor:ActorRef

  def actorSystem:ActorSystem

  lazy val log = Logging(actorSystem, classOf[RestRoutes])

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  //POST・・create
  //GET ・・read
  //PUT ・・replace
  //DLETE・・delete
  val route: Route = {
    pathPrefix("widget" / Segments){json =>
      post {
        entity(as[Widget]){widget =>
          log.info("jsonで渡された値：" + widget.toString)
          val responce:Future[EventResponce] = (widgetDeliveryServerActor ? Create(widget)).mapTo[EventResponce]
          onSuccess(responce) {
            case WidgetCreated(_) => complete(StatusCodes.OK, "created widget")
            //StatusCodeはConflictの方がいい？
            case WidgetExists => complete(StatusCodes.BadRequest, "widget is exists")
            case _ => complete(StatusCodes.BadRequest, "request error")
          }
        }
      } ~
      get {
        //TODO:WidgetDeliveryServerActorを取得する処理
        complete(json.toString)
      } ~
      put {
        //TODO:WidgetDeliveryServerActorを編集する処理
        complete(json.toString)
      } ~
      delete {
        //TODO:WidgetDeliveryServerActorを削除する処理
        complete(json.toString)
      }
    }
  }
}

