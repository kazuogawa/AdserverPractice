package com.example.widgetdeliveryserver.actor

object WidgetDelivery {
  case class Widget(
    widgetId   :Int,
    adSlotNum  :Int,
    recoSlotNum:Int
  )
  case class Widgets(widgets:Seq[Widget])
  case class Create(widget:Widget)
  case class Get(widgetId:Int)
  case class Edit(widget:Widget)
  case class Delete(widgetId:Int)
  case object WidgetList

  sealed trait EventResponse
  case class WidgetCreated(widget: Widget) extends EventResponse
  case object WidgetExists extends EventResponse
  case class WidgetDeleted(widgetId:Int) extends EventResponse
  case class WidgetNotFound(widgetId:Int) extends EventResponse
  case object WidgetIdIsNotNumeric extends EventResponse
  case class WidgetEdited(widget:Widget) extends EventResponse
}
