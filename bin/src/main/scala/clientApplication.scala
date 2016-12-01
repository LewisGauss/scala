import scalafx.application.JFXApp;
import akka.actor._;
import playerActor._;
object clientApplication extends App{
  val system = ActorSystem("Snake And Ladder");
  val clientActor = system.actorOf(Props[playerActor]);
  
 clientActor ! startGame;
 clientActor!playerTurn;
 clientActor!Move();
}