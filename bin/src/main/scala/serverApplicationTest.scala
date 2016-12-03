import scalafx.application.JFXApp;
import akka.actor._;
import playerActor._;

object serverApplicationTest extends App {
  val system = ActorSystem("SnakeAndLadder");
  val sa = system.actorOf(Props[serverActor]);
  val p1 = system.actorOf(Props(new playerActor(sa, "John")));
  val p2 = system.actorOf(Props(new playerActor(sa, "Peter")));

  println("START");
  scala.io.StdIn.readLine()

  println("p1 connect")
  scala.io.StdIn.readLine()
  p1 ! ConnectToServer

  println("p2 connect")
  scala.io.StdIn.readLine()
  p2 ! ConnectToServer

  println("p1 ready")
  scala.io.StdIn.readLine()
  p1 ! Ready

  println("p2 ready")
  scala.io.StdIn.readLine()
  p2 ! Ready

  println("=====*****=======")
  for (i <- 1 to 10) {
    println("p1 play")
    scala.io.StdIn.readLine()
    p1 ! Play

    println("p2 play")
    scala.io.StdIn.readLine()
    p2 ! Play
  }
}