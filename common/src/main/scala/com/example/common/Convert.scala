package com.example.common

object Convert {
  //stringを安全にintに変換
  def safeStringToInt(str: String): Option[Int] = {
    import scala.util.control.Exception._
    catching(classOf[NumberFormatException]) opt str.toInt
  }
}
