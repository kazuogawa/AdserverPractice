package com.example.adserver.actor

import akka.actor.Actor
import com.example.common.domain.model.WidgetDelivery.WidgetAdPost

class AdServerActor extends Actor{
  override def receive: Receive = {
    case WidgetAdPost(widgetId:Int, adSlotNum:Int) =>
    case _ =>
  }
}
