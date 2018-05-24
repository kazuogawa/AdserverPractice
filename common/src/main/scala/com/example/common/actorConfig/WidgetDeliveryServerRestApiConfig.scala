package com.example.common.actorConfig

import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.{Duration, FiniteDuration}

case class WidgetDeliveryServerRestApiConfig (
  host:String,
  port:String
)

object WidgetDeliveryServerRestApiConfig {
  private def requestTimeout(akkaConfig:Config):Timeout = {
    val time = WidgetDeliveryServerConfig.getString("akka.http.server.request-timeout")
    val d    = Duration(time)
    FiniteDuration(d.length,d.unit)
  }
  val WidgetDeliveryServerConfig: Config = ConfigFactory.load()
  val restConfig:WidgetDeliveryServerRestApiConfig = WidgetDeliveryServerRestApiConfig(
    WidgetDeliveryServerConfig.getString("widget_delivery_server.host"),
    WidgetDeliveryServerConfig.getString("widget_delivery_server.port")
    //requestTimeout(config)
  )
}

