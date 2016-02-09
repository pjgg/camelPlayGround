package org.pablo.camel.transformations

import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelExtension
import akka.event.Logging
import akka.util.Timeout.durationToTimeout

object InitAkka {

  def main(args: Array[String]): Unit = {
    
    val system = ActorSystem("waterfallTransformations")
    val log = Logging(system, InitAkka.getClass().getName())

    val config = ConfigFactory.load()

    val camel = CamelExtension(system)
    implicit val executionContext = system.dispatcher

    val restConsumer = system.actorOf(Props(new RestConsumer))

    val camelContext = camel.context
    val activationFuture = camel.activationFutureFor(restConsumer)(timeout = 10 seconds, executionContext)
    
    activationFuture.onComplete {
      case Success(consumerActor) => { log.info("Up and running!") }
      case Failure(ex)            => { log.error(ex.getMessage) }
    }
    
  }

}