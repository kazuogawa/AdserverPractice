package com.example.common.config

import com.typesafe.config.{Config, ConfigFactory}

object RecommendServerConfig {
  val config: Config = ConfigFactory.load()
  //TODO:actor名やportをどうにかrecoとRestApiのConfigファイル上に残せないか探す
  val recoServerPath = "akka.tcp://RecoServer@0.0.0.0:2550/user/reco"
}
