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

  for {
    n <- 1 to 5
  } {
    bmbActor ! BoundedMailBoxActor.BmbMessage1(n)
    bmbActor ! BoundedMailBoxActor.BmbMessage2(s"message-$n")

    Thread.sleep(500)
  }

  override def receive: Receive = {

    case msg =>
      log.info(s"Client received response: $msg")
  }
}
