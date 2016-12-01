import akka.actor._;

class playerActor extends Actor{
  import playerActor._;
  import serverActor._;
  
  var isReady : Boolean = false;
  var myTurn : Boolean = false;
  var currentRoom : Room = null;
  
  def connectToServer {
    
  }
  def receive = {
    case StartGame => {
      println("START GAME");
    }
    case PlayerTurn => {
      myTurn = true;
      println("MY TURN");
    }
    case Move() => {
      println("MOVING THE PLAYERS");
    }
    case RegistrationSuccess(room : Room) => {
      this.currentRoom = room;
      println("ROOM : " + room)
    }
  }
}

object playerActor {
  case object StartGame
  case object PlayerTurn
  case class RegistrationSuccess(room : Room)
  case object RegistrationFail
  case class Move()
 
}