package com.example.widgetdeliveryserver

import akka.actor._
import akka.actor.Props
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer

import com.example.widgetdeliveryserver.actor.WidgetDeliveryServerActor
import com.example.widgetdeliveryserver.restapi.RestRoutes
import com.example.common.config.{AdServerConfig, RecommendServerConfig, WidgetDeliveryServerConfig}
import com.example.common.domain.model.WidgetDelivery.WidgetsUpdate
import com.example.widgetdeliveryserver.scopt.{WidgetDeliveryServerArgs, parser}

import scala.io.StdIn
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App with RestRoutes {
  val widgetArgs: WidgetDeliveryServerArgs = parser.toWidgetDeliveryServerArgs(args)
  val redisConfig = WidgetDeliveryServerConfig.getRedisConfig(widgetArgs.isTest)
  //これをimplicitしないとbindAndHandleできなかった
  implicit val widgetDeliveryServerActorSystem: ActorSystem  = ActorSystem("widgetDeliveryServer", WidgetDeliveryServerConfig.config)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  //val widgetDeliveryServerActor: ActorRef = widgetDeliveryServerActorSystem.actorOf(Props[WidgetDeliveryServerActor])
  val widgetDeliveryServerActor: ActorRef = widgetDeliveryServerActorSystem.actorOf(Props(new WidgetDeliveryServerActor(redisConfig)))
  implicit val executionContext: ExecutionContextExecutor = widgetDeliveryServerActorSystem.dispatcher
  val bindingFuture:Future[ServerBinding] = Http().bindAndHandle(route,WidgetDeliveryServerConfig.restConfig.host,WidgetDeliveryServerConfig.restConfig.port.toInt)

  implicit val adServerActorSystem: ActorSelection = widgetDeliveryServerActorSystem.actorSelection(AdServerConfig.adServerPath)
  implicit val recoServerActorSystem: ActorSelection = widgetDeliveryServerActorSystem.actorSelection(RecommendServerConfig.recoServerPath)


  //schedulerで定期的に、redisからデータを読み込む
  //1分毎に更新処理を繰り返す
  widgetDeliveryServerActorSystem.scheduler.schedule(50 milliseconds, 1 minute, widgetDeliveryServerActor, WidgetsUpdate)

  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => widgetDeliveryServerActorSystem.terminate()) // and shutdown when done
}
