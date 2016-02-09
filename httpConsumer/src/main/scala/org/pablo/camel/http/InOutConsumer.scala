package org.pablo.camel.http

import akka.camel.{ CamelMessage, Consumer }
import scala.concurrent.duration._
import akka.actor.ActorLogging
import com.typesafe.config.Config

class InOutConsumer(url: String) extends Consumer with ActorLogging {

  val endpointUri: String = url

  override def replyTimeout = 500 millis

  def this() {
    this("jetty:http://localhost:8282/")
  }

  def this(urlParams: Config) {
    this(urlParams.getString("component") + ":" + urlParams.getString("protocol") + "://" + urlParams.getString("host") + ":" + urlParams.getString("port") + "/" + urlParams.getString("path"))
  }

  def receive = {
    case msg: CamelMessage => { 
      var msgBody = msg.bodyAs[String]
      log.info(msgBody)
      sender() ! ("Hello %s" format msgBody) 
      }
    case _                 => { /* ... */ }
  }

}



