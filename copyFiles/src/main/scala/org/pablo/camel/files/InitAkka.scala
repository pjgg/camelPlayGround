package org.pablo.camel.files

import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.RouteBuilder
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.camel.CamelExtension
import akka.event.Logging
import akka.actor.Props

object InitAkka {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("copyFiles")
    val log = Logging(system, InitAkka.getClass().getName())

  // val config = ConfigFactory.load()

    val camel = CamelExtension(system)
    implicit val executionContext = system.dispatcher

    val fileConsumer = system.actorOf(Props(new FileConsumer()))

    val camelContext = camel.context
    val activationFuture = camel.activationFutureFor(fileConsumer)(timeout = 10 seconds, executionContext)
    
    val template = CamelExtension(system).template
    template.asyncSendBody("file:/home/kuasars/workspaceScala/copyFiles/src/main/resources/inBound", "test")
    
    activationFuture.onComplete {
      case Success(consumerActor) => { log.info("Up and running!") }
      case Failure(ex)            => { log.error(ex.getMessage) }
    }
  }

}