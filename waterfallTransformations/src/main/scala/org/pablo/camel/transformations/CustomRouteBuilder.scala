package org.pablo.camel.transformations

import akka.actor.ActorSystem
import org.apache.camel.builder.RouteBuilder
import akka.actor.ActorRef

class CustomRouteBuilder extends RouteBuilder {
  
  def configure {
    from("file:/home/kuasars/workspaceScala/waterfallTransformations/src/main/resources/inBound")
    .log("data -> inbound")
    .to("file:/home/kuasars/workspaceScala/waterfallTransformations/src/main/resources/outBound")
    .log("data -> outbound")
  }
  
}