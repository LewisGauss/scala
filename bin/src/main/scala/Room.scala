import scalafx.collections.ObservableBuffer;
import java.util.Observable;

class Room extends Observable{
  var playerList : ObservableBuffer[player] = new ObservableBuffer[player]();
  var turnCounter = 0;
  var playersReady = 0;
  
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
    return playerList.get(turnCounter % playerList.size)
  }
  
  def play:Int = {
    val dice : Int = ((Math.random() * (6-1) )+ 1.0).toInt
    this.currentTurnPlayer.position += dice
    turnCounter+=1;  
    return dice
  }
}