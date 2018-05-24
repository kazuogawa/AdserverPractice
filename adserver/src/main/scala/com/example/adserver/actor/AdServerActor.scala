package com.example.adserver.actor

import akka.actor.Actor

class AdServerActor extends Actor{
  override def receive: Receive = {
    case m => println(s"received message $m")
  }
}
