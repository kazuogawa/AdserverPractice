package com.example.common.domain.model

import com.example.common.domain.model.Slots._

object WidgetDelivery {
  case class Widget(
    widgetId   :String,
    adSlotNum  :Int,
    recoSlotNum:Int
  )
  case class Widgets(widgets:Seq[Widget])
  case class Create(widget:Widget)
  case class Get(widgetId:Int)
  case class Edit(widget:Widget)
  case class Delete(widgetId:Int)
  case object WidgetList
  case class WidgetAdPost(
    widgetId :Int,
    adSlotNum:Int
  )
  case class WidgetRecommendPost(
    widgetId:Int,
    recoSlotNum:Int
  )

  sealed trait EventResponse
  case class  WidgetCreated(widget: Widget) extends EventResponse
  //TODO:もっといい名前を見つける
  case class  WidgetResponse(widgetId:String, adSlotNum:Int, recoSlotNum:Int) extends EventResponse
  //TODO:もっといい名前を見つける
  case class  WidgetData(widgetId:Int, adSlots:List[AdSlot],recommendSlots:List[RecommendSlot]) extends EventResponse
  case class  WidgetEdited(widget:Widget) extends EventResponse
  case object WidgetExists extends EventResponse
  case class  WidgetDeleted(widgetId:Int) extends EventResponse
  object      WidgetNotFound extends EventResponse
  case object WidgetIdIsNotNumeric extends EventResponse
}
