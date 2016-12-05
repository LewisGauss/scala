import java.util.Observer;
import java.util.Observable;
import scalafx.collections.ObservableBuffer;
import akka.actor._;
import playerActor._;

trait RoomObserver extends Observer{
  override def update(room : Observable, playerList : Object) ={
    for( p <- playerList.asInstanceOf[ObservableBuffer[player]]){
      p.ref ! PlayerList(playerList.asInstanceOf[ObservableBuffer[player]])
    }
  }
}