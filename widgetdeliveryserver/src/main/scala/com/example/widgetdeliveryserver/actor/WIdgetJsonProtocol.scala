package com.example.widgetdeliveryserver.actor

import com.example.widgetdeliveryserver.actor.WidgetDelivery.Widget
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object WIdgetJsonProtocol extends DefaultJsonProtocol{
  implicit val format: RootJsonFormat[Widget] = jsonFormat3(Widget)
}
