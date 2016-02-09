package org.pablo.camel.files

import akka.actor.ActorSystem
import org.apache.camel.builder.RouteBuilder
import akka.actor.ActorRef

class CustomRouteBuilder extends RouteBuilder {
  def configure {
    from("file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/inBound").to("file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/outBound")
  }
}