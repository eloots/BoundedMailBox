package poc.boundedmailbox

import akka.actor.{Actor, ActorLogging, Props}

object BoundedMailBoxActor {
  case class Response(msg: String)
  sealed trait BMB_MSGS
  case class BmbMessage1(n: Int) extends BMB_MSGS
  case class BmbMessage2(s: String) extends BMB_MSGS


  def props(): Props = Props(new BoundedMailBoxActor)
}

class BoundedMailBoxActor extends Actor with ActorLogging {
  import BoundedMailBoxActor._

  override def receive: Receive = {

    case msg @ BmbMessage1(n) =>
      log.info(s"BMB Received: $msg")
      Thread.sleep(5000) // Don't do this
      sender() ! Response(n.toString)

    case msg @ BmbMessage2(s) =>
      log.info(s"BMB Received: $msg")
      Thread.sleep(5000)
      sender() ! Response(s)
  }
}
