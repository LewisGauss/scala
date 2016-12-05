import scalafx.application.JFXApp;
import akka.actor._;
import playerActor._;
import serverActor._;

object serverApplicationTest extends App{
  
   val system = ActorSystem("SNAKEGAME");
   val serverActor = system.actorOf(Props[serverActor]);
   
   val playerActor = system.actorOf(Props[playerActor]);
   
   serverActor ! Connect("player 1",playerActor);
}