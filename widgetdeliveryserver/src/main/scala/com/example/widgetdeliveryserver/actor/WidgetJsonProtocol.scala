package com.example.widgetdeliveryserver.actor

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.widgetdeliveryserver.actor.WidgetDelivery._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import com.example.common.parser.SlotJsonProtocol._

object WidgetJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val widgetFormat: RootJsonFormat[Widget] = jsonFormat3(Widget)
  implicit val widgetsFormat: RootJsonFormat[Widgets] = jsonFormat1(Widgets)
  implicit val widgetDataFormat:RootJsonFormat[WidgetData] = jsonFormat3(WidgetData)
  implicit val widgetAdPostFormat:RootJsonFormat[WidgetAdPost] = jsonFormat2(WidgetAdPost)
  implicit val widgetRecommendPostFormat:RootJsonFormat[WidgetRecommendPost] = jsonFormat2(WidgetRecommendPost)
}
