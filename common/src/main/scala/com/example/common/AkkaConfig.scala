package com.example.common

import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import com.example.common.actorConfig.WidgetDeliveryServerConfig

class AkkaConfig {
  import scala.concurrent.duration._
  def requestTimeout(akkaonfig:Config):Timeout = {
    val time = config.getString("akka.http.server.request-timeout")
    val d    = Duration(time)
    FiniteDuration(d.length,d.unit)
  }
  val config: Config = ConfigFactory.load()
  val widgetDeliveryServerConf:WidgetDeliveryServerConfig = WidgetDeliveryServerConfig(
    config.getString("widget_delivery_server.host"),
    config.getString("widget_delivery_server.port")
    //requestTimeout(config)
  )
}