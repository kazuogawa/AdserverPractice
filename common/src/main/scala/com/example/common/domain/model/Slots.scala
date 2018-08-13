package com.example.common.domain.model

object Slots {
  case class AdSlot(
    creativeId:Int,
    title   :String,
    alt     :String,
    imageUrl:String,
    url     :String)


  case class RecommendSlot(
    recommendId:Int,
    title   :String,
    alt     :String,
    imageUrl:String,
    url     :String)

  sealed trait SlotResponse

  //adServerから返ってくるadSlot用。widgetIdは相性の良いadの種類や属性を判別するため
  case class AdSlots(widgetId:String, slots:List[AdSlot]) extends SlotResponse


  //recommendServerから返ってくるrecommendSlot用。widgetIdは相性の良いrecommendの種類や属性を判別するため
  case class RecommendSlots(widgetId:String, slots:List[RecommendSlot]) extends SlotResponse
}
