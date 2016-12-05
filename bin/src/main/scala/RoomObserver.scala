import java.util.Observer;
import java.util.Observable;
import scala.collection.mutable.ArrayBuffer;
import akka.actor._;
import playerActor._;

trait RoomObserver extends Observer{
  override def update(room : Observable, playerList : Object) ={
    for( p <- room.asInstanceOf[Room].playerList){
      p.ref ! PlayerList(room.asInstanceOf[Room].playerList)
    }
  }
}