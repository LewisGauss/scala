import akka.actor._;
import scala.collection.mutable.ArrayBuffer;
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.pipe
import akka.util.Timeout
import scala.concurrent.duration._
import serverActor._
import akka.pattern.ask
import akka.util.Timeout
import scalafx.application.Platform
import scala.util.Failure;

//class playerActor( server : ActorRef,name : String) extends Actor{
class playerActor(val control: startingWindowController#Controller, sa: ActorRef, aname: String) extends Actor {
  import playerActor._

  val serverActor = context.actorSelection("akka.tcp://SnakeGame@192.168.0.172:1305/user/serverActor")
  var isReady: Boolean = false;
  var myTurn: Boolean = false;
  var currentRoom: Room = null;
  var playerID: Int = 0;

  def inGame: Receive = {
    case Win(name: String) => {
      Platform.runLater({
        clientApplication.gameBoardController.Win(name)
      });
    }
    case PlayerTurn => {
      myTurn = true;
      Platform.runLater({
        clientApplication.gameBoardController.myTurn();
      });
    }
    case Play => {
      if (myTurn) {
        implicit val timeout = Timeout(10 seconds)
        serverActor ? RollDice(playerID) foreach{
          case x:String => {}
          case Failure =>{
            Platform.runLater({
              clientApplication.gameBoardController.rollButton.disable = false
            });
          }
        }
      } else {
        println("Not my turn")
      }
    }
    case Move(room: Room) => {
      Platform.runLater({
        clientApplication.currentRoom = room;
        clientApplication.gameBoardController.updateUI()
      });

    }
    case DiceResult(result: Int) => {
      println("dice result :" + result);
    }
  }
  def receive = {
    case ConnectToServer(name: String) => {
      implicit val timeout = Timeout(5 seconds)
      serverActor ? Connect(name, self) pipeTo sender
    }
    case StartGame => {
      println("START GAME");
      context.become(inGame)
      Platform.runLater({
        clientApplication.startGameBoard();
      });
    }
    case Ready => {
      println(clientApplication.currentRoom)
      serverActor ! PReady(clientApplication.currentRoom)
    }

    case RegistrationSuccess(room: Room,id : Int) => {
      Platform.runLater({
        clientApplication.currentRoom = room
      });
      this.playerID = id;
      this.currentRoom = room;

      //clientApplication.replaceSceneContent("roomWindow.fxml")
    }
    case PlayerList(playerList: ArrayBuffer[player]) => {
      Platform.runLater({
        clientApplication.updatePlayerList(playerList);

      });

    }
    case RegistrationFail => {
      println("GAME STARTED");

    }
  }
}

object playerActor {
  case object Ready
  case class DiceResult(result: Int)
  case class ConnectToServer(name: String)
  case object StartGame
  case object PlayerTurn
  case class RegistrationSuccess(room: Room,playerID : Int)
  case object RegistrationFail
  case class Move(room: Room)
  case object Play
  case class PlayerList(playerList: ArrayBuffer[player])
  case class Win(name: String)
}