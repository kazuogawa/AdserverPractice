package com.example.widgetdeliveryserver.redis
import com.redis._
import com.example.common.domain.model.WidgetDelivery.Widget
import com.example.common.redis.{RedisFunctions, redisConfig}

object RedisWidget {
  def apply(config:redisConfig):RedisWidgetImpl = {
    val redisClient = new RedisClient(config.host, config.port)
    new RedisWidgetImpl(redisClient)
  }
}

//TODO:RedisWidgetってなにしている名前のclassかわからないよね。。わかる名前に変える
class RedisWidgetImpl(redisClient:RedisClient) extends RedisFunctions {

  def getWidget(widgetId:String):Option[Widget] = {
    val widgetOptMap: Option[Map[String, String]] = redisClient.hgetall1(s"widgetid:$widgetId:slotnum")
    widgetOptMap match {
      case Some(widgetMap:Map[String,String]) =>
        if(!isDefineAtWidgetKey(widgetMap)) {
          loggerEmptyMessage(s"$widgetId widget hash")
          None
        }
        else Some(mapToWidget(widgetId, widgetMap))
      case None =>
        loggerEmptyMessage(s"$widgetId widget hash")
        None
    }
  }

  def getWidgets:Option[Set[Widget]] = {
    val widgetsIdsOptSetOpt:Option[Set[Option[String]]] = redisClient.smembers("widgetids")
    widgetsIdsOptSetOpt match {
      case Some(widgetIdsSetOpt:Set[Option[String]]) =>
        val widgetIds:Set[String] = for{
          Some(s) <- widgetIdsSetOpt
        } yield s
        if(widgetIds.isEmpty) {
          loggerEmptyMessage("widgets")
          None
        }
        else {
          val widgetOpt: Set[Option[Widget]] = widgetIds.map(id => getWidget(id))
          val widgets = for {
            Some(w) <- widgetOpt
          } yield w
          Some(widgets)
        }
      case None =>
        loggerEmptyMessage("widgets")
        None
    }
  }

  private def isDefineAtWidgetKey(widgetMap:Map[String,String]):Boolean =
    widgetMap.isDefinedAt("ad_slot_num") && widgetMap.isDefinedAt("reco_slot_num")

  private def mapToWidget(widgetId:String, widgetMap:Map[String,String]):Widget =
    Widget(widgetId, widgetMap.getOrElse("ad_slot_num", "0").toInt, widgetMap.getOrElse("reco_slot_num", "0").toInt)
}
