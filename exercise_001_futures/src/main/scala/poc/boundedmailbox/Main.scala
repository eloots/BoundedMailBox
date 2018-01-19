package poc.boundedmailbox

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import scala.util.{ Failure, Success }

import scala.concurrent.duration._

object Main {
  def main(args: Array[String]): Unit = {

    val system = ActorSystem("bmb-poc-system")
    import scala.concurrent.ExecutionContext.Implicits.global

    val client = system.actorOf(Client.props(), "bmb-client")

    implicit val askTimeOut: Timeout = 60.seconds
    system.log.debug(s"Setting timeout to $askTimeOut")

    for {
      n <- 1 to 5
    } {
      val response1 = client ? BoundedMailBoxActor.BmbMessage1(n)

      response1.mapTo[BoundedMailBoxActor.Response].onComplete {
        case Success(r) => system.log.debug(s"Main got: $r")
        case Failure(ex) => system.log.debug(s"Main: request timed out: $ex")
      }

      val response2 = client ? BoundedMailBoxActor.BmbMessage2(s"Message $n")

      response2.mapTo[BoundedMailBoxActor.Response].onComplete {
        case Success(r) => system.log.debug(s"Main got: $r")
        case Failure(ex) => system.log.debug(s"Main: request timed out: $ex")
      }

      Thread.sleep(500)
    }
  }
}
