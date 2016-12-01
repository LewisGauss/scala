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
    case startGame => {
      println("START GAME");
    }
    case playerTurn => {
      myTurn = true;
      println("MY TURN");
    }
    case Move() => {
      println("MOVING THE PLAYERS");
    }
    case registrationSuccess(room : Room) => {
      this.currentRoom = room;
      println("ROOM : " + room)
    }
  }
}

object playerActor {
  case object startGame;
  case object playerTurn;
  case class registrationSuccess(room : Room);
  case object registrationFail;
  case class Move();
 
}