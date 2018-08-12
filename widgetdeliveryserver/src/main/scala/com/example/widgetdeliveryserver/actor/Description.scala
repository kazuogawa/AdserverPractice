package com.example.widgetdeliveryserver.actor

import com.example.common.domain.model.WidgetDelivery.Widget

object Description {
  case class WidgetDescription(widgets:List[Widget], newWidgetId:Int){
    !widgets.exists(_.widgetId == newWidgetId)
  }
}
