import scalafx.collections.ObservableBuffer;

object RoomHandler {
   var rooms : ObservableBuffer[Room] = new ObservableBuffer[Room]();
   
   def getRoom() : Room = {
     for( room <- rooms){
       if(room.playerList.size() <= 4){
         return room;
       }
     }
     
     var room = new Room();
     rooms += room 
     return room
   }
}