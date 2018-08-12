package com.example.widgetdeliveryserver.redis

import org.slf4j.{Logger, LoggerFactory}

//TODO:このcase classはどこにおけばいいのだろう
case class redisConfig(host:String, port:Int)
trait RedisFunctions {
  val logger: Logger = LoggerFactory.getLogger("redisLog")
  def loggerEmptyMessage(name:String): Unit = {
   logger.info(name + " is empty")
  }
}
