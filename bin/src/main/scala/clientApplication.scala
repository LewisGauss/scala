import scalafx.application.JFXApp;
import akka.actor._;
import playerActor._;

object clientApplication extends App {
  val system = ActorSystem("SnakeAndLadder");
  val sa = system.actorOf(Props[serverActor]);
  val p1 = system.actorOf(Props(new playerActor(sa,"John")));
  val p2 = system.actorOf(Props(new playerActor(sa,"Peter")));
  
  println("START");
  scala.io.StdIn.readLine()
  
  println("p1 connect")
  scala.io.StdIn.readLine()
  p1 ! ConnectToServer
  
  println("p2 connect")
  scala.io.StdIn.readLine()
  p2 ! ConnectToServer
}