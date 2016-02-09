package org.pablo.camel.transformations

import akka.camel.{ CamelMessage, Consumer }
import scala.concurrent.duration._
import akka.actor.ActorLogging
import com.typesafe.config.Config
import akka.actor.Props
import akka.camel.CamelExtension
import akka.camel.Ack
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.{read, write}
import org.json4s.native.Serialization

class RestConsumer extends Consumer with ActorLogging {

  val endpointUri: String = "jetty:http://localhost:8282/inbound"
  
  val fileConsumer = context.actorOf(Props[FileConsumer], name = "fileConsumer")
  
  camel.context.addRoutes(new CustomRouteBuilder)
  implicit val formats = Serialization.formats(NoTypeHints)
  override def replyTimeout = 500 millis

  def receive = {
    case msg: CamelMessage => { 
      var msgBody = msg.bodyAs[String]
      log.info("sending:" + msgBody + " to fileConsumer")
      camel.template.asyncSendBody("file:/home/kuasars/workspaceScala/waterfallTransformations/src/main/resources/inBound", msgBody.toUpperCase)
      sender() ! write(serviceResponseMsgDto(1, "completed")) 
      }
    
    case _                 => { /* ... */ }
    
  }

}

case class serviceResponseMsgDto (code: Int, msg: String)

