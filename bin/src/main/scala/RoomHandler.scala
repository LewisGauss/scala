import scalafx.collections.ObservableBuffer;
import java.util.Observer;
object RoomHandler {
   var rooms : ObservableBuffer[Room] = new ObservableBuffer[Room]();
   var roomId = 0;
   def getRoom(observer : Observer) : Room = {
     for( room <- rooms){
       if(room.playerList.size <= 4){
         return room;
       }
     }
     
     var room = new Room(roomId);
     room.addObserver(observer)
     roomId +=1;
     rooms += room 
     return room
   }
}