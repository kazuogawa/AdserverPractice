package com.example.widgetdeliveryserver.actor

import akka.actor.Actor
import com.example.widgetdeliveryserver.actor.WidgetDelivery._

class WidgetDeliveryServerActor extends Actor{
  var widgets = Vector.empty[Widget]
  override def receive: Receive = {
    case Create(postWidget)     => {
      if(widgets.contains(postWidget)) WidgetExists
      else {
        widgets = widgets :+ postWidget
        sender() ! WidgetCreated(postWidget)
      }
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
