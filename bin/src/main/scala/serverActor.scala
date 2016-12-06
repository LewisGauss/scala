import akka.actor._;
import RoomHandler._;
import akka.pattern.ask;
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.TimeoutException
import scala.concurrent.ExecutionContext.Implicits.global

class serverActor extends Actor with RoomObserver {
  import serverActor._;
  import playerActor._;

  var activeRoom: Room = null;
  context.setReceiveTimeout(10 seconds)
  def active: Receive = {
    case Connect(playerName: String, ref: ActorRef) => {
      sender ! RegistrationFail
    }

    case RollDice(playerID : Int) => {
      sender ! "OK"
      if (activeRoom.currentTurnPlayer.id == playerID) {
        val result = activeRoom.play;
        for (p <- activeRoom.playerList) {
          p.ref ! DiceResult(result)
          p.ref ! Move(activeRoom)
          println(p.position)
          if (p.position >= 100) {
            p.ref ! Win(p.name)
          }
        }
        self ! AssignTurn
      } else {
        println("NOT YOUR TURN");
      }
    }
    case AssignTurn => {
      activeRoom.currentTurnPlayer.ref ! PlayerTurn
    }
    case ReceiveTimeout => {
      println("TIMEOUT")
      val result = activeRoom.play;
        for (p <- activeRoom.playerList) {
          p.ref ! DiceResult(result)
          p.ref ! Move(activeRoom)
          println(p.position)
          if (p.position >= 100) {
            p.ref ! Win(p.name)
          }
        }
        self ! AssignTurn
    }
  }
  def receive = {
    case Connect(playerName: String, ref: ActorRef) => {
      val room: Room = RoomHandler.getRoom(this)
      var id = (Math.random() * (99999999-1) +1).toInt
      var p = player(playerName, ref,id)
      room.addPlayer(p)

      println("ADDED PLAYER " + playerName);
      println("ROOM : " + room);
      sender ! RegistrationSuccess(room,p.id)
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
      for (r <- RoomHandler.rooms) {
        if (r.id == room.id) {
          r.playerReady();
          if (r.start) {
            for (p <- r.playerList) {
              p.ref ! StartGame
            }
            activeRoom = r;
            context.become(active);
            println("Game starting ");
            self ! AssignTurn
          } else {
            println(r.playersReady)
            println(r.start)
            for (p <- r.playerList) {
              p.ref ! PlayerList(r.playerList);
            }

          }
        } else {
          println(r)
          println(room)
        }
      }

    }

    case _ => {
      println("RECEIVED something");
    }
  }
}

object serverActor {
  case class Connect(playerName: String, ref: ActorRef)
  case class PReady(room: Room);
  case class RollDice(playerID : Int);
  case object AssignTurn
}