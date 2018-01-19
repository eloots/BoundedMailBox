package poc.boundedmailbox

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object BoundedMailBoxActor {
  case class Response(msg: String)
  sealed trait BMB_MSGS
  case class BmbMessage1(n: Int, sndr: Option[ActorRef] = None) extends BMB_MSGS
  case class BmbMessage2(s: String, sndr: Option[ActorRef] = None) extends BMB_MSGS


  def props(): Props = Props(new BoundedMailBoxActor)
}

class BoundedMailBoxActor extends Actor with ActorLogging {
  import BoundedMailBoxActor._

  override def receive: Receive = {

    case msg @ BmbMessage1(n, sndr) =>
      log.info(s"BMB Received: $msg")
      Thread.sleep(5000) // Don't do this - this serves as a simulation of a blocking call
      sndr.get ! Response(n.toString)

    case msg @ BmbMessage2(s, sndr) =>
      log.info(s"BMB Received: $msg")
      Thread.sleep(5000) // Don't do this - this serves as a simulation of a blocking call
      sndr.get ! Response(s)
  }
}
