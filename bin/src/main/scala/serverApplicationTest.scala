import scalafx.application.JFXApp;
import akka.actor._;
import scalafx.Includes._;
import scalafx.scene.Scene;
import playerActor._;
<<<<<<< HEAD
import serverActor._;

object serverApplicationTest extends App{
  
   val system = ActorSystem("SNAKEGAME");
   val serverActor = system.actorOf(Props[serverActor]);
   
   val playerActor = system.actorOf(Props[playerActor]);
   
   serverActor ! Connect("player 1",playerActor);
}
=======
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.Group;
import scalafx.collections.ObservableBuffer
import scalafx.event.Event
import scala.collection.mutable.Set

object serverApplicationTest extends JFXApp {
  val system = ActorSystem("SnakeAndLadder");
  val sa = system.actorOf(Props[serverActor]);
  val p1 = system.actorOf(Props(new playerActor(sa, "John")));
  val p2 = system.actorOf(Props(new playerActor(sa, "Peter")));

  
  //================DEMO FOR UI=================
  def getCoordinates(position : Int) :Coor ={
    val x = ((position -1)%10) * 60
    val y = 500 - (Math.floor((position -1)/10) * 60)
    return new Coor(x,y)
  }
  val rec1 = new Rectangle {
    width = 30
    height = 30
    fill = LightGreen
    x = 0
    y = 580
  }
  val rec2 = new Rectangle {
    width = 20
    height = 20
    fill = Blue
    x = 0
    y = 500
  }

  val players = new ObservableBuffer[Rectangle]();
  players+= rec1
  players+= rec2
  var counter = 1;
  stage = new JFXApp.PrimaryStage {
    title = "demo"
    width = 600
    height = 600
    scene = new Scene {
      root = new Group{
        children = players
      }
      
      onMouseClicked = (e:Event)=>{
        counter += 1
        val pos = getCoordinates(counter);
        
        rec2.x = pos.x
        rec2.y = pos.y
      }
    }
  }
  
  //===================END OF DEMO FOR UI================
  
  /*
  //==========DEMO FOR ACTORS===============
  println("START");
  Thread.sleep(1000);

  println("p1 connect")
  Thread.sleep(1000);
  p1 ! ConnectToServer

  println("p2 connect")
  Thread.sleep(1000);
  p2 ! ConnectToServer

  println("p1 ready")
  Thread.sleep(1000);
  p1 ! Ready

  println("p2 ready")
  Thread.sleep(1000);
  p2 ! Ready

  println("=====*****=======")
  for (i <- 1 to 5) {
    println("p1 play")
    Thread.sleep(1000);
    p1 ! Play

    println("p2 play")
    Thread.sleep(1000);
    p2 ! Play
  
  }

  
  //========== END OF DEMO FOR ACTORS===============
  
  */
    
}

class Coor( val x : Double, val y : Double)
>>>>>>> bd8154709ae02cc1ce60537da491d2782f54dafd
