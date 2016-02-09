package org.pablo.camel.files

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.camel.CamelExtension
import akka.event.Logging
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.LoggingLevel

object Init {

  def main(args: Array[String]): Unit = {
    
    val camelContext = new DefaultCamelContext()
    
    val routeBuilder = new RouteBuilder {
     from("file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/inBound")
     .to("file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/outBound")
     .log(LoggingLevel.INFO, "pabloLog", "lalala")
     }
     camelContext.addRoutes(routeBuilder)
     camelContext.start
     while (true) {}
 
  }

}