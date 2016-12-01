import scalafx.collections.ObservableBuffer;

class Room{
  var playerList : ObservableBuffer[player] = new ObservableBuffer[player]();
  var turnCounter = 0;
  
  def addPlayer( p : player){
    playerList += p;
  }
  
  def start : Boolean = {
    var playersReady = 0;
    for( player <- playerList){
      if(player.isReady){
        playersReady +=1 
      }
    }
    
    return playersReady >= 2 
  }
  
  def currentTurnPlayer : player = {
    return playerList.get(turnCounter % playerList.size)
  }
  
  def play = {
    turnCounter+=1;  
  }
}