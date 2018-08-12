package com.example.widgetdeliveryserver.restapi

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import com.example.common.Convert._
import com.example.common.domain.model.Slots._
import com.example.common.domain.model.WidgetDelivery._
import com.example.widgetdeliveryserver.actor.WidgetJsonProtocol._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


//TODO:RestApiも別プロジェクトとして切り出す
trait RestRoutes {

  def widgetDeliveryServerActor:ActorRef

  def widgetDeliveryServerActorSystem:ActorSystem

  def adServerActorSystem  : ActorSelection
  def recoServerActorSystem: ActorSelection

  lazy val log = Logging(widgetDeliveryServerActorSystem, classOf[RestRoutes])

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  //POST・・create
  //GET ・・read
  //PUT ・・replace
  //DLETE・・delete
  val route: Route = {
    pathPrefix("widget" / "list"){
      get {
        val response:Future[Widgets] = (widgetDeliveryServerActor ? WidgetList).mapTo[Widgets]
        onSuccess(response) {
          case widgets:Widgets => complete(widgets)
          case _               => complete(StatusCodes.NotFound, "widgets is not found")
        }
      }
    } ~
    pathPrefix("widget" / Segments){param =>
      post {
        entity(as[Widget]){widget =>
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
        //ここもっとFutureを使って、並列に動かすことが出来る気がする。この書き方だとブロッキングしている？
        val response: Future[EventResponse] = safeStringToInt(param.head) match {
          case Some(widgetId) => (widgetDeliveryServerActor ? widgetId).mapTo[EventResponse]
          case None           => Future(WidgetNotFound)
        }
        onSuccess(response) {
          case WidgetResponse(widgetId, adSlotNum, recoSlotNum) =>
            //adのみ、recoのみの場合即座に返せるように
            //TODO:通信が失敗した時どのように届くかを決めていない
            val adServerResponse  :Future[AdSlots] =
              if(adSlotNum > 0) (adServerActorSystem ? WidgetAdPost(widgetId,adSlotNum)).mapTo[AdSlots]
              else Future(AdSlots(widgetId, Nil))
            val recoServerResponse:Future[RecommendSlots] =
              if(recoSlotNum > 0) (recoServerActorSystem ? WidgetRecommendPost(widgetId,recoSlotNum)).mapTo[RecommendSlots]
              else Future(RecommendSlots(widgetId, Nil))

            val widgetdataResponse:Future[WidgetData] = for {
              adSlots:AdSlots          <- adServerResponse
              recoSlots:RecommendSlots <- recoServerResponse
            } yield WidgetData(widgetId, adSlots.slots, recoSlots.slots)

            complete(StatusCodes.OK,widgetdataResponse)
          case _ => complete(StatusCodes.NotFound, "widget is not found")
        }
      } ~
      put {
        entity(as[Widget]){widget =>
          val response:Future[EventResponse] = (widgetDeliveryServerActor ? Edit(widget)).mapTo[EventResponse]
          onSuccess(response) {
            case WidgetEdited(editWidget) => complete(StatusCodes.OK, s"widget with id ${editWidget.widgetId} is edited")
            case _                        => complete(StatusCodes.NotFound, s"widget with id ${widget.widgetId} is not found")
          }
        }
      } ~
      delete {
        val response: Future[EventResponse] = safeStringToInt(param.head) match {
          case Some(widgetId) => (widgetDeliveryServerActor ? Delete(widgetId)).mapTo[EventResponse]
          case None           => Future(WidgetIdIsNotNumeric)
        }
        onSuccess(response) {
          case WidgetDeleted(widgetId)  => complete(StatusCodes.OK        , s"delete $widgetId widget")
          case WidgetNotFound           => complete(StatusCodes.NotFound  , "widget is not found")
          case WidgetIdIsNotNumeric     => complete(StatusCodes.BadRequest, "request widgetId is not numeric")
          case _                        => complete(StatusCodes.BadRequest, "delete request error")
        }
      }
    }
  }
}

