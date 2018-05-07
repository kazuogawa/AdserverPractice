package com.example.widgetdeliveryserver

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.HttpApp
import akka.stream.ActorMaterializer
import com.example.common.actorConfig.WidgetDeliveryServerConfig
import com.example.common.AkkaConfig
import com.example.widgetdeliveryserver.actor.WidgetDeliveryServerActor
import com.example.widgetdeliveryserver.restapi.RestRoutes
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App with RestRoutes {
  val akkaConfig = new AkkaConfig
  val config:WidgetDeliveryServerConfig = akkaConfig.widgetDeliveryServerConf

  //これをimplicitしないとbindAndHandleできなかった
  implicit val actorSystem: ActorSystem  = ActorSystem("widgetDeliveryServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //
  val widgetDeliveryServerActor: ActorRef = actorSystem.actorOf(Props[WidgetDeliveryServerActor])
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  val bindingFuture:Future[ServerBinding] = Http().bindAndHandle(route,config.host,config.port.toInt)
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => actorSystem.terminate()) // and shutdown when done
}
