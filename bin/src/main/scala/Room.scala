import scala.collection.mutable.ArrayBuffer;
import java.util.Observable;
import scala.collection.mutable.HashMap

class Room extends Observable  with Serializable{
  var playerList : ArrayBuffer[player] =  ArrayBuffer[player]();
  var turnCounter = 0;
  var playersReady = 0;
  var snl = HashMap(
  (4,14),
  (9,31),
  (17,7),
  (21,42),
  (28,84),
  (51,67),
  (54,34),
  (62,19),
  (64,60),
  (71,91),
  (80,100),
  (87,24),
  (93,73),
  (95,75),
  (98,79)
  );
  
  def addPlayer( p : player){
    playerList += p;
    
    this.setChanged();
    this.notifyObservers(playerList);
  }
  
  def playerReady(){
    playersReady += 1;
  }
  def start : Boolean = {
    if(playersReady == playerList.size 
        && playerList.size >= 2){
      return true
    }
    return false
  }
  
  def currentTurnPlayer : player = {
    return playerList(turnCounter % playerList.size)
  }
  
  def play:Int = {
    val dice : Int = ((Math.random() * (6-1) )+ 1.0).toInt
    this.currentTurnPlayer.position += dice
    val hit = snl.get(this.currentTurnPlayer.position)
    hit match {
      case None => {}
      case Some(x) => this.currentTurnPlayer.position = x
    }
    turnCounter+=1;  
    return dice
  }
}