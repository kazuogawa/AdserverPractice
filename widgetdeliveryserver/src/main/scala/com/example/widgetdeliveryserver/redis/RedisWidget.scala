package com.example.widgetdeliveryserver.redis
import com.redis._
import com.example.common.domain.model.WidgetDelivery.Widget

//TODO:RedisWidgetってなにしている名前のclassかわからないよね。。わかる名前に変える
class RedisWidget(config:redisConfig) extends RedisFunctions {
  val redisClient = new RedisClient(config.host, config.port)

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
        val widgetIds:Set[String] = widgetIdsSetOpt.toSet[String]
        if(widgetIds.isEmpty) {
          loggerEmptyMessage("widgets")
          None
        }
        else Option(widgetIds.map(id => getWidget(id)).toSet[Widget])
      case None =>
        loggerEmptyMessage("widgets")
        None
    }
  }

  def isDefineAtWidgetKey(widgetMap:Map[String,String]):Boolean =
    widgetMap.isDefinedAt("ad_slot_num") && widgetMap.isDefinedAt("reco_slot_num")

  def mapToWidget(widgetId:String, widgetMap:Map[String,String]):Widget =
    Widget(widgetId, widgetMap.getOrElse("ad_slot_num", "0").toInt, widgetMap.getOrElse("reco_slot_num", "0").toInt)
}
