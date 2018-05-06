package com.example.widgetdeliveryserver

import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.example.common.actorConfig.WidgetDeliveryServerConfig
import com.example.common.AkkaConfig
import com.example.widgetdeliveryserver.actor.WidgetDeliveryServerActor
import com.example.widgetdeliveryserver.restapi.RestRoutes

import scala.concurrent.Future

object Main extends App with RestRoutes {
  val akkaConfig = new AkkaConfig
  val config:WidgetDeliveryServerConfig = akkaConfig.widgetDeliveryServerConf
  println("test host is " + config.host)

  val actorSystem: ActorSystem  = ActorSystem("widgetDeliveryServer")
  val widgetDeliveryServerActor = actorSystem.actorOf(Props[WidgetDeliveryServerActor])
  widgetDeliveryServerActor ! 123
  val api: RestRoutes = new RestRoutes(actorSystem)

  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val bindingFuture:Future[ServerBinding] = Http().bindAndHandle(api.route,config.host,config.port.toInt)
}
