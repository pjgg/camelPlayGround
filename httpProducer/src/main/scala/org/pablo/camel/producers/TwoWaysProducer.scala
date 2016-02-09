package org.pablo.camel.producers

import akka.camel.Producer
import akka.actor.Actor
import com.typesafe.config.Config
import scala.concurrent.duration._
import akka.camel.CamelMessage

class TwoWaysProducer(url: String) extends Actor with Producer {

  val endpointUri: String = url
  
  def this() {
    this("http://localhost:8282/example")
  }

  def this(urlParams: Config) {
    this(urlParams.getString("protocol") + "://" + urlParams.getString("host") + ":" + urlParams.getString("port") + "/" + urlParams.getString("path"))
  }
  
  override def oneway: Boolean = false
  
  def upperCase(msg: CamelMessage) = msg.mapBody {
    body: String => body.toUpperCase
  }
  
  override def transformOutgoingMessage(msg: Any) = msg match {
    case msg: CamelMessage => upperCase(msg)
  }
  
}