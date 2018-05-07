package com.example.widgetdeliveryserver.actor

import akka.actor.Actor
import com.example.widgetdeliveryserver.actor.WidgetDelivery._


class WidgetDeliveryServerActor extends Actor{
  var widgets = Vector.empty[Widget]
  override def receive: Receive = {
    case Create(postWidget)     => {
      if(widgets.contains(postWidget)) WidgetExists
      else {
        widgets :+ postWidget
        sender() ! WidgetCreated(postWidget)
      }
    }
      //TODO:Get処理をかく
    case Get(widgetId)    =>
    //TODO:Edit処理をかく
    case Edit(json)       =>
    //TODO:Delete処理をかく
    case Delete(widgetId) =>
    case _ => println("error")
  }
}
