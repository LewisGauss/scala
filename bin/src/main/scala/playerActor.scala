import akka.actor._;
import scalafx.collections.ObservableBuffer;
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.pipe
import akka.util.Timeout
import scala.concurrent.duration._

//class playerActor( server : ActorRef,name : String) extends Actor{
class playerActor(val control: startingWindowController#Controller, server: ActorRef, name: String) extends Actor{
  import playerActor._
  import serverActor._
  
  val serverActor = context.actorSelection("akka.tcp://SnakeAndLadder@127.0.0.1:5150/user/serverActor")
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
       serverActor ! Connect(name, self)
    }
    case StartGame => {
      println("START GAME");
      context.become(inGame)
    }
    case Ready => {
      serverActor ! PlayerReady(currentRoom)
    }
    
    case RegistrationSuccess(room : Room) => {
      this.currentRoom = room;
      println("Registered \n ROOM : " + room)
    }
    case PlayerList( playerList : ObservableBuffer[player]) =>{
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
  case class PlayerList(playerList : ObservableBuffer[player])
 
}