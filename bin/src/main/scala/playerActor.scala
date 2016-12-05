import akka.actor._;
import scala.collection.mutable.ArrayBuffer;
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.pipe
import akka.util.Timeout
import scala.concurrent.duration._
 import serverActor._
 import akka.pattern.ask
 import akka.util.Timeout


//class playerActor( server : ActorRef,name : String) extends Actor{
class playerActor(val control: startingWindowController#Controller, sa: ActorRef, name: String) extends Actor{
  import playerActor._
  
  val serverActor = context.actorSelection("akka.tcp://SnakeGame@127.0.0.1:1305/user/serverActor")
  var isReady : Boolean = false;
  var myTurn : Boolean = false;
  var currentRoom : Room = null;
  val playerID : player = null;
  
  def inGame : Receive= {
    case PlayerTurn => {
      myTurn = true;
      println("MY TURN");
    }
    case Play => {
      if(myTurn){
        serverActor ! RollDice
      }else{
        println("Not my turn")
      }
    }
    case Move() => {
       for(p <- currentRoom.playerList){
          println( p.name+ " : " + p.position);
       }
    }
    case DiceResult(result : Int) => {
      println("dice result :"+result);
    }
  }
  def receive = {
    case ConnectToServer =>{
      implicit val timeout = Timeout(5 seconds)
       serverActor ? Connect(name, self) pipeTo sender
    }
    case StartGame => {
      println("START GAME");
      clientApplication.startGameBoard();
      context.become(inGame)
    }
    case Ready => {
      serverActor ! PlayerReady(currentRoom)
    }
    
    case RegistrationSuccess(room : Room) => {
      this.currentRoom = room;
      println("Registered \n ROOM : " + room)
      
    
      //clientApplication.replaceSceneContent("roomWindow.fxml")
    }
    case PlayerList( playerList : ArrayBuffer[player]) =>{
      var i = 0;
      for(p <- playerList){
        println("Player list received ")
        println("---------------------------------")
        println("Player "+ i + " : " + p.name);
        i+=1;
      }
    }
  }
}

object playerActor {
  case object Ready
  case class DiceResult( result : Int)
  case object ConnectToServer
  case object StartGame
  case object PlayerTurn
  case class RegistrationSuccess(room : Room)
  case object RegistrationFail
  case class Move()
  case object Play
  case class PlayerList(playerList : ArrayBuffer[player])
 
}