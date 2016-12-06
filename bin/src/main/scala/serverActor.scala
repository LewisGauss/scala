import akka.actor._;

class serverActor extends Actor with RoomObserver {
  import serverActor._;
  import playerActor._;

  var activeRoom: Room = null;

  def active: Receive = {
    case Connect(playerName: String, ref: ActorRef) => {
      sender ! RegistrationFail
    }
    case RollDice => {
      val result = activeRoom.play;
      for (p <- activeRoom.playerList) {
        p.ref ! DiceResult(result)
        p.ref ! Move(activeRoom)
      }
      self ! AssignTurn
    }
    case AssignTurn =>{
      activeRoom.currentTurnPlayer.ref ! PlayerTurn
    }
  }
  def receive = {
    case Connect(playerName: String, ref: ActorRef) => {
      val room: Room = RoomHandler.getRoom(this)
      room.addPlayer(player(playerName, ref))

      println("ADDED PLAYER " + playerName);
      println("ROOM : " + room);
      sender ! RegistrationSuccess(room)
      //test start
      /*
      if(room.playerList.size ==2){
        println("full");
         for (p <- room.playerList) {
          p.ref ! StartGame
        }
        activeRoom = room;
        context.become(active);
        println("Game starting ");
        self!AssignTurn
      }
      * */
      
      //test end
    }
    case PReady(room: Room) => {
      println("RECEIVED READY");
      room.playerReady();
      if (room.start) {
        for (p <- room.playerList) {
          p.ref ! StartGame
        }
        activeRoom = room;
        context.become(active);
        println("Game starting ");
        self!AssignTurn
      }else{
        sender ! PlayerList(room.playerList);
      }
    }
    
    case _=>{
      println("RECEIVED something");
    }
  }
}

object serverActor {
  case class Connect(playerName: String, ref: ActorRef)
  case class PReady(room: Room);
  case class RollDice(playerId: Int);
  case object AssignTurn
}