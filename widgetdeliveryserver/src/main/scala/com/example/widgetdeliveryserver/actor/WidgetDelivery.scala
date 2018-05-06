package com.example.widgetdeliveryserver.actor

object WidgetDelivery {
  case class Widget(
    widgetId:Int,
    adSlotNum:Int,
    recoSlotNum:Int
  )
  case class Create(json:String)
  case class Request(widgetId:Int)
  case class Edit(json:String)
  case class Delete(widgetId:Int)

  sealed trait EventResponce
  //object分けたほうがいい？
  case class WidgetCreated(widget: Widget) extends EventResponce
  case object WidgetExists extends EventResponce
}
