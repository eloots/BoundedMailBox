package poc.boundedmailbox

import akka.actor.Status
import akka.actor.{Actor, ActorLogging, ActorRef, DeadLetter, Props}

object BoundedMailboxDeadLetters {

  def props(client: ActorRef): Props = Props(new BoundedMailboxDeadLetters(client))
}

class BoundedMailboxDeadLetters(client: ActorRef) extends Actor with ActorLogging {

  context.system.eventStream.subscribe(self, classOf[DeadLetter])

  override def receive: Receive = {

    case msg @ DeadLetter(BoundedMailBoxActor.BmbMessage1(n, sndr), msgSender, msgRecipient) if msgSender == client =>
      //log.debug(s"~~~> Received dead letter: ${msg.message} from ${msg.sender} with recipient = ${msg.recipient}")
      sndr.get ! Status.Failure(new Exception(s"Bounded mailbox actor rejected (${msg.message}) !"))

    case msg @ DeadLetter(BoundedMailBoxActor.BmbMessage2(n, sndr), msgSender, msgRecipient) if msgSender == client =>
      //log.debug(s"~~~> Received dead letter: ${msg.message} from ${msg.sender} with recipient = ${msg.recipient}")
      sndr.get ! Status.Failure(new Exception(s"Bounded mailbox actor rejected (${msg.message}) !"))

    case msg: DeadLetter =>
      log.debug(s"~~~> Received dead letter(other): ${msg.message} from ${msg.sender} with recipient = ${msg.recipient}")
  }

}
