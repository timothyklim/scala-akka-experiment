package com.scalaakka

import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._

case class WorkerMessage(msg: String)
case class MasterMessage(msg: String)
case class DoMaster()

class Worker extends Actor {
  def receive = {
    case WorkerMessage(id) =>
      sender ! WorkerMessage("%s. Yeah, %s" format(id, this.hashCode()))
  }
}

class Master(numberOfMessages: Int, listener: ActorRef) extends Actor {
  val workerRouter = context.actorOf(
  Props[Worker].withRouter(RoundRobinRouter(100)),
    name = "workerRouter"
  )
  var completeWorks = 0

  def receive = {
    case DoMaster() =>
      for (currentNumber <- 0 until numberOfMessages)
        workerRouter ! WorkerMessage(currentNumber.toString)
    case WorkerMessage(message) =>
//      println("Master.receive: %s" format message)

      completeWorks += 1
      if (completeWorks == numberOfMessages) {
        listener ! MasterMessage("Done!")
        context.stop(self)
      }
  }
}

class Listener extends Actor {
  def receive = {
    case MasterMessage(message) =>
      println("Listener: %s" format message)
      context.system.shutdown()
  }
}

object Main extends App {
  val system = ActorSystem("AkkaTestSystem")
  val listener = system.actorOf(Props[Listener], name = "listener")

  val master = system.actorOf(Props(new Master(1000000, listener)), name = "master")

  master ! DoMaster()
}
