package com.example.widgetdeliveryserver.actor

import akka.actor.Actor
import com.example.common.domain.model.WidgetDelivery._
import com.example.common.redis.redisConfig
import com.example.widgetdeliveryserver.redis.RedisWidget
import org.slf4j.{Logger, LoggerFactory}

class WidgetDeliveryServerActor(redisConf:redisConfig) extends Actor{
  var widgets = Vector.empty[Widget]
  val r = RedisWidget(redisConf)
  val logger: Logger = LoggerFactory.getLogger("widgetDeliveryActorLog")

  override def preStart(): Unit = {
    super.preStart()
    r.getWidgets match {
      case Some(w:Set[Widget]) => widgets = w.toVector
      case None =>
    }
    logger.info("preStart completed")
  }
  override def receive: Receive = {
    case WidgetsUpdate => logger.info("update completed")

    case Create(postWidget)=>
      if(widgets.contains(postWidget)) WidgetExists
      else {
        widgets = widgets :+ postWidget
        sender() ! WidgetCreated(postWidget)
      }
    case Get(widgetId)    =>
      val widgetOpt: Option[Widget] = widgets.find(_.widgetId == widgetId)
      widgetOpt match {
        case Some(widget) => sender() ! WidgetResponse(widget.widgetId,widget.adSlotNum,widget.recoSlotNum)
        case None         => sender() ! WidgetNotFound
    }
    case Edit(postWidget) =>
      val widgetOpt = widgets.find(_.widgetId == postWidget.widgetId)
      widgetOpt match {
        case Some(widget) =>
          //1回で終わらせられる方法があれば、書き換える
          widgets = widgets filterNot widget.==
          widgets = widgets :+ postWidget
          sender() ! WidgetEdited(postWidget)
        case None => sender() ! WidgetNotFound
      }
    case Delete(widgetId) =>
      val widgetOpt: Option[Widget] = widgets.find(_.widgetId == widgetId)
      widgetOpt match {
        case Some(widget) =>
          widgets = widgets filterNot widget.==
          sender() ! WidgetDeleted(widgetId)
        case None => sender() ! WidgetNotFound
      }
    case WidgetList       => sender() ! Widgets(widgets)
    case _                => println("error")
  }
}
