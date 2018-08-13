package com.example.common.config

import com.typesafe.config.{Config, ConfigFactory}

object AdServerConfig {
  val config: Config = ConfigFactory.load()
  //TODO:actor名やportをどうにかAdとRestApiのConfigファイル上に残せないか探す
  val adServerPath = "akka.tcp://AdServer@0.0.0.0:2551/user/ad"
}
