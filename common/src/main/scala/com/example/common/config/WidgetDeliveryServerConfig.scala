package com.example.common.config

import akka.util.Timeout
import com.example.common.redis.redisConfig
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.{Duration, FiniteDuration}

case class WidgetDeliveryServerRestApiConfig (
  host:String,
  port:String
)

object WidgetDeliveryServerConfig {
  private def requestTimeout(akkaConfig:Config):Timeout = {
    val time = config.getString("akka.http.server.request-timeout")
    val d    = Duration(time)
    FiniteDuration(d.length,d.unit)
  }
  val config: Config = ConfigFactory.load()
  val restConfig:WidgetDeliveryServerRestApiConfig = WidgetDeliveryServerRestApiConfig(
    config.getString("widget_delivery_server.host"),
    config.getString("widget_delivery_server.port")
    //requestTimeout(config)
  )

  def getRedisConfig(isTest:Boolean):redisConfig =
    if(isTest) redisConfig(config.getString("redis.dev.hostname")  ,config.getInt("redis.dev.port"))
    else       redisConfig(config.getString("redis.trunk.hostname"),config.getInt("redis.trunk.port"))
}

