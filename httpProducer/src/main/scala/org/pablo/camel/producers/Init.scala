package org.pablo.camel.producers

import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelExtension
import akka.camel.CamelMessage
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout.durationToTimeout
import org.apache.camel.model.RouteDefinition

object Init {

  def main(args: Array[String]): Unit = {
    
    val system = ActorSystem("httpProducer")
    val log = Logging(system, Init.getClass().getName())
    
    val config = ConfigFactory.load()
    
    val camel = CamelExtension(system)
    implicit val executionContext = system.dispatcher

    val producer = system.actorOf(Props(new TwoWaysProducer(config.getConfig("producer"))))
    
    implicit val timeout = akka.util.Timeout(10 seconds)
    
    val camelContext = camel.context
    val activationFuture = camel.activationFutureFor(producer)(timeout = 10 seconds, executionContext)

    activationFuture.onComplete {
      case Success(consumerActor) => { log.info("Up and running!" ) }
      case Failure(ex)            => { log.error(ex.getMessage)}
    }
    
    //val futureResponse = producer ? CamelMessage("pablo", Map(CamelMessage.MessageExchangeId -> "123")) 

    system.scheduler.schedule(0 milliseconds, 50 milliseconds, producer,  CamelMessage("pablo", Map(CamelMessage.MessageExchangeId -> "123")))
  }

}