import akka.actor._;
import java.util.Observer;
import java.util.Observable;
import scalafx.collections.ObservableBuffer;

case class player( name : String, ref : ActorRef) {
  var position = 1;
  var isReady = false;
  
  
}