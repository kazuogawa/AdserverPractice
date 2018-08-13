package com.example.common.domain.model

import com.example.common.domain.model.Slots._

object WidgetDelivery {
  case class Widget(
    widgetId   :String,
    adSlotNum  :Int,
    recoSlotNum:Int
  )
  case class Widgets(widgets:Seq[Widget])
  case object WidgetsInit
  case object WidgetsUpdate
  case class Create(widget:Widget)
  case class Get(widgetId:String)
  case class Edit(widget:Widget)
  case class Delete(widgetId:String)
  case object WidgetList
  case class WidgetAdPost(
    widgetId :String,
    adSlotNum:Int
  )
  case class WidgetRecommendPost(
    widgetId:String,
    recoSlotNum:Int
  )

  sealed trait EventResponse
  case class  WidgetCreated(widget: Widget) extends EventResponse
  //TODO:もっといい名前を見つける
  case class  WidgetResponse(widgetId:String, adSlotNum:Int, recoSlotNum:Int) extends EventResponse
  //TODO:もっといい名前を見つける
  case class  WidgetData(widgetId:String, adSlots:List[AdSlot],recommendSlots:List[RecommendSlot]) extends EventResponse
  case class  WidgetEdited(widget:Widget) extends EventResponse
  case object WidgetExists extends EventResponse
  case class  WidgetDeleted(widgetId:String) extends EventResponse
  object      WidgetNotFound extends EventResponse
  case object WidgetIdIsNotNumeric extends EventResponse
}
