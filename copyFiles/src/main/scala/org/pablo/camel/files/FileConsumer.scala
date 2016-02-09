package org.pablo.camel.files

import akka.camel.{ CamelMessage, Consumer }
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import org.apache.camel.processor.idempotent.FileIdempotentRepository
import com.sun.istack.Builder
import java.io.File
import akka.actor.Props

class FileConsumer extends Consumer {
  
  def endpointUri = "file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/inBound"
 
  def receive = {
    case msg: CamelMessage => println("received %s" format msg.bodyAs[String])
  }
  
}


