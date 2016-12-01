import akka.actor._;

class serverActor extends Actor{
  import serverActor._;
  import playerActor._;
  
  var activeRoom:Room = null;
  
  def active : Receive = {
    case Connect(playerName : String, ref : ActorRef) => {
       sender ! RegistrationFail
    }
    case RollDice => {
       activeRoom.play;
       for(p <- activeRoom.playerList){
         p.ref ! Move()
       }
       activeRoom.currentTurnPlayer.ref ! PlayerTurn
       
    }
  }
  def receive = {
    case Connect(playerName : String, ref : ActorRef) =>{
       val room :Room = RoomHandler.getRoom()
       room.addPlayer(player(playerName,ref))
       
       println("ADDED PLAYER "+playerName);
       println("ROOM : "+room);
       sender ! RegistrationSuccess(room)
    }
    case Ready(room : Room) => {
      if(room.start){
        for(p <- room.playerList){
          p.ref ! StartGame
        }
      }
      activeRoom = room;
      context.become(active);
    }
  }
}

object serverActor{
  case class Connect(playerName : String, ref : ActorRef)
  case class Ready(room : Room);
  case class RollDice(playerId : Int);
}