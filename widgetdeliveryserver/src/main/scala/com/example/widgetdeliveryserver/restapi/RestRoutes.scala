package com.example.widgetdeliveryserver.restapi

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import com.example.common.Convert._
import com.example.widgetdeliveryserver.actor.WidgetDelivery._
import com.example.widgetdeliveryserver.actor.WidgetJsonProtocol._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait RestRoutes {

  def widgetDeliveryServerActor:ActorRef

  def actorSystem:ActorSystem

  lazy val log = Logging(actorSystem, classOf[RestRoutes])

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  //POST・・create
  //GET ・・read
  //PUT ・・replace
  //DLETE・・delete
  val route: Route = {
    pathPrefix("widget" / Segments){param =>
      post {
        entity(as[Widget]){widget =>
          log.info("jsonで渡された値：" + widget.toString)
          val response:Future[EventResponse] = (widgetDeliveryServerActor ? Create(widget)).mapTo[EventResponse]
          onSuccess(response) {
            case WidgetCreated(createdWidget)
                              => complete(StatusCodes.OK , s"created ${createdWidget.widgetId} widget")
            //StatusCodeはConflictの方がいい？
            case WidgetExists => complete(StatusCodes.BadRequest, "widget is exists")
            case _            => complete(StatusCodes.BadRequest, "create request error")
          }
        }
      } ~
      get {
        //TODO:WidgetDeliveryServerActorを取得する処理
        complete(param.toString)
      } ~
      put {
        //TODO:WidgetDeliveryServerActorを編集する処理
        complete(param.toString)
      } ~
      delete {
        val response: Future[EventResponse] = safeStringToInt(param.head) match {
          case Some(widgetId) => (widgetDeliveryServerActor ? Delete(widgetId)).mapTo[EventResponse]
          case None           => Future(WidgetIdIsNotNumeric)
        }
        onSuccess(response) {
          case WidgetDeleted(widgetId)  => complete(StatusCodes.OK        , s"delete $widgetId widget")
          case WidgetNotFound(widgetId) => complete(StatusCodes.NotFound  , s"$widgetId widget is not found")
          case WidgetIdIsNotNumeric     => complete(StatusCodes.BadRequest, "request widgetId is not numeric")
          case _                        => complete(StatusCodes.BadRequest, "delete request error")
        }
      }
    }
  }
}

