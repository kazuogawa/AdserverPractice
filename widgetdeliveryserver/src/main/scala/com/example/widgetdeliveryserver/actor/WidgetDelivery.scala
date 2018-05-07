package com.example.widgetdeliveryserver.actor

object WidgetDelivery {
  case class Widget(
    widgetId   :Int,
    adSlotNum  :Int,
    recoSlotNum:Int
  )
  case class Create(widget:Widget)
  case class Get(widgetId:Int)
  case class Edit(widget:Widget)
  case class Delete(widgetId:Int)

  sealed trait EventResponce
  case class WidgetCreated(widget: Widget) extends EventResponce
  case object WidgetExists extends EventResponce
}
