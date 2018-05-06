package com.example.widgetdeliveryserver.actor

import akka.actor.Actor
import com.example.widgetdeliveryserver.actor.WidgetDelivery._
import WIdgetJsonProtocol._

class WidgetDeliveryServerActor extends Actor{
  var widgets = Vector.empty[Widget]
  override def receive: Receive = {
    case widgetId:Int      => println("widgetId is " + widgetId)
    case Create(json)      => //val widget = json.parseJson.convertTo[Widget]
    case Request(widgetId) =>
    case Edit(json)        =>
    case Delete(widgetId)  =>
  }
}
