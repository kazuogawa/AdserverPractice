package com.example.widgetdeliveryserver.actor

import com.example.widgetdeliveryserver.actor.WidgetDelivery.Widget

object Description {
  case class WidgetDescription(widgets:List[Widget], newWidgetId:Int){
    !widgets.exists(_.widgetId == newWidgetId)
  }
}
