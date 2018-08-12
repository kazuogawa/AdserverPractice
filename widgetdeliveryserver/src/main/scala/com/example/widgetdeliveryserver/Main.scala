package com.example.widgetdeliveryserver

import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import com.example.common.actorConfig.WidgetDeliveryServerRestApiConfig._
import com.example.widgetdeliveryserver.actor.WidgetDeliveryServerActor
import com.example.widgetdeliveryserver.restapi.RestRoutes
import akka.stream.ActorMaterializer
import com.example.common.actorConfig.{AdServerConfig, RecommendServerConfig}

import scala.io.StdIn
import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App with RestRoutes {

  //これをimplicitしないとbindAndHandleできなかった
  implicit val widgetDeliveryServerActorSystem: ActorSystem  = ActorSystem("widgetDeliveryServer", WidgetDeliveryServerConfig)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val widgetDeliveryServerActor: ActorRef = widgetDeliveryServerActorSystem.actorOf(Props[WidgetDeliveryServerActor])
  implicit val executionContext: ExecutionContextExecutor = widgetDeliveryServerActorSystem.dispatcher
  val bindingFuture:Future[ServerBinding] = Http().bindAndHandle(route,restConfig.host,restConfig.port.toInt)


  implicit val adServerActorSystem: ActorSelection = widgetDeliveryServerActorSystem.actorSelection(AdServerConfig.adServerPath)
  implicit val recoServerActorSystem: ActorSelection = widgetDeliveryServerActorSystem.actorSelection(RecommendServerConfig.recoServerPath)


  //schedulerで定期的に、redisからデータを読み込む

  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => widgetDeliveryServerActorSystem.terminate()) // and shutdown when done
}
