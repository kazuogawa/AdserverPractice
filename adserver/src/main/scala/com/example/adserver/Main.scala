package com.example.adserver

import akka.actor.{ActorSystem, Props}
import com.example.adserver.actor.AdServerActor
import com.example.common.config.AdServerConfig
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

object Main extends App {
  val config: Config = AdServerConfig.config
  val adServerActor = ActorSystem("AdServer", config)
  adServerActor.actorOf(Props[AdServerActor], "ad")
}
