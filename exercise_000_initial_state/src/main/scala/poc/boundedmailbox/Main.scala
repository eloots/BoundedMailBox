package poc.boundedmailbox

import akka.actor.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {

    val system = ActorSystem("bmb-poc-system")
    system.actorOf(Client.props(), "bmb-client")
  }
}
