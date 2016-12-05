import scalafx.collections.ObservableBuffer;
import java.util.Observer;
object RoomHandler {
   var rooms : ObservableBuffer[Room] = new ObservableBuffer[Room]();
   
   def getRoom(observer : Observer) : Room = {
     for( room <- rooms){
       if(room.playerList.size <= 4){
         return room;
       }
     }
     
     var room = new Room();
     room.addObserver(observer)
     rooms += room 
     return room
   }
}