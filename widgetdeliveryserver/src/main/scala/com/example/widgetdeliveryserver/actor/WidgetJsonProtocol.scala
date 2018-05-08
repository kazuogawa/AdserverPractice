package com.example.widgetdeliveryserver.actor

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.widgetdeliveryserver.actor.WidgetDelivery.{Widget, Widgets}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object WidgetJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val widgetFormat: RootJsonFormat[Widget] = jsonFormat3(Widget)
  implicit val widgetsFormat: RootJsonFormat[Widgets] = jsonFormat1(Widgets)
}
