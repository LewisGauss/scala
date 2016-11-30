import akka.actor._;

class serverActor extends Actor{
  import serverActor._;
  import playerActor._;
  
  var activeRoom = null;
  
  def active : Receive = {
    case Connect(playerName : String, ref : ActorRef) => {
       sender ! registrationFail
    }
    case rollDice => {
       activeRoom.play;
       for(p <- activeRoom.playerList){
         p.ref ! move
       }
       activeRoom.currentTurnPlayer.ref ! playerTurn
       
    }
  }
  def receive = {
    case Connect(playerName : String, ref : ActorRef) =>{
       val room :Room = RoomHandler.getRoom()
       room.addPlayer(player(playerName,ref))
       sender ! registrationSuccess(room)
    }
    case ready(room : Room) => {
      if(room.start){
        for(p <- room.playerList){
          p.ref ! start
        }
      }
      activeRoom = room;
      context.become(active);
    }
  }
}

object serverActor{
  case class Connect(playerName : String, ref : ActorRef)
  case class ready(room : Room);
  case class rollDice(playerId : Int);
}