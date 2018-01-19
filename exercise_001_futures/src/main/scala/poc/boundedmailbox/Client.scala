package poc.boundedmailbox

import akka.actor.{Actor, ActorLogging, Props}


object Client {

  def props(): Props = Props(new Client)
}

class Client extends Actor with ActorLogging {
  private val bmbActor =
    context.actorOf(
      BoundedMailBoxActor
        .props()
        .withMailbox("bounded-mailbox"),
      "bounded-mailbox-actor"
    )

  private val bmbDeadLetters =
    context.actorOf(BoundedMailboxDeadLetters.props(self), "dead-letter-monitor")

  override def receive: Receive = {

    case msg: BoundedMailBoxActor.BmbMessage1 =>
      bmbActor ! msg.copy(sndr = Some(sender()))

    case msg: BoundedMailBoxActor.BmbMessage2 =>
      bmbActor ! msg.copy(sndr = Some(sender()))

    case msg @ BoundedMailBoxActor.Response(r) =>
      log.debug(s"Got a response from BMB Actor: $msg")
  }
}
