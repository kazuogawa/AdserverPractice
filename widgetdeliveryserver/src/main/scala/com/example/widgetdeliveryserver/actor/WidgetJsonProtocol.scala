package com.example.widgetdeliveryserver.actor

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.widgetdeliveryserver.actor.WidgetDelivery.Widget
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object WidgetJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val format: RootJsonFormat[Widget] = jsonFormat3(Widget)
}
