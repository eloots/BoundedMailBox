package poc.boundedmailbox

import akka.actor.{Actor, ActorLogging, ActorRef, DeadLetter, Props}

object BoundedMailboxDeadLetters {

  def props(client: ActorRef): Props = Props(new BoundedMailboxDeadLetters(client))
}

class BoundedMailboxDeadLetters(client: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case msg: DeadLetter if msg.sender == client =>
      log.info(s"~~~> Received dead letter: ${msg.message} from ${msg.sender} with recipient = ${msg.recipient}")
  }

  override def preStart(): Unit = {
    context.system.eventStream.subscribe(self, classOf[DeadLetter])
  }

  override def postStop(): Unit = {
    context.system.eventStream.unsubscribe(self)
  }

}
