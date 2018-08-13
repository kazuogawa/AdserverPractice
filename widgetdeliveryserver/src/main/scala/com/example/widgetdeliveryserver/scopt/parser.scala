package com.example.widgetdeliveryserver.scopt

case class WidgetDeliveryServerArgs(
  isTest:Boolean = false
)

object parser {
  def toWidgetDeliveryServerArgs(args:Array[String]):WidgetDeliveryServerArgs = {
    val widgetDeliveryServerParser = new scopt.OptionParser[WidgetDeliveryServerArgs]("scopt") {
      head("scopt", "3.x")
      opt[Unit]("test").action((x, c) => c.copy(isTest = true)).text("test環境で実行したい時に--testってoptionつけてね")
    }
    widgetDeliveryServerParser.parse(args, WidgetDeliveryServerArgs()) match {
      case Some(w) => w
      case None    => WidgetDeliveryServerArgs()
    }
  }
}
