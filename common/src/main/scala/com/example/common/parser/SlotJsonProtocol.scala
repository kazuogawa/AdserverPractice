package com.example.common.parser

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.common.domain.model.Slots.{AdSlot, RecommendSlot}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object SlotJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val AdSlotFormat:RootJsonFormat[AdSlot] = jsonFormat5(AdSlot)
  implicit val RecommendSlotFormat:RootJsonFormat[RecommendSlot] = jsonFormat5(RecommendSlot)

}

