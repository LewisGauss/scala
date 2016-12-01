import scalafx.application.JFXApp;
import akka.actor._;
import playerActor._;

object clientApplication extends App {
  val system = ActorSystem("SnakeAndLadder");
  val clientActor = system.actorOf(Props[playerActor]);

  clientActor ! StartGame;
  println("SHOUDL BE TURN");
  clientActor ! PlayerTurn;
  println("SHOULD BE MOVE");
  clientActor ! Move();
}