package org.pablo.camel.http

import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelExtension
import akka.util.Timeout.durationToTimeout
import akka.event.Logging
import com.typesafe.config.ConfigFactory

object Init {

  def main(args: Array[String]): Unit = {
    
    val system = ActorSystem("httpConsumer")
    val log = Logging(system, Init.getClass().getName())
    
    val config = ConfigFactory.load()
    
    val camel = CamelExtension(system)
    implicit val executionContext = system.dispatcher

    val httpConsumer = system.actorOf(Props(new InOutConsumer(config.getConfig("consumer"))))

    val camelContext = camel.context
    val activationFuture = camel.activationFutureFor(httpConsumer)(timeout = 10 seconds, executionContext)

    activationFuture.onComplete {
      case Success(consumerActor) => { log.info("Up and running!" ) }
      case Failure(ex)            => { log.error(ex.getMessage)}
    }

  }

}