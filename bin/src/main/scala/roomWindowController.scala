import scalafxml.core.macros.sfxml
import scalafx.scene.control.ListView
import scalafx.Includes._
import scalafx.event.ActionEvent
import akka.pattern.ask
import scala.concurrent.duration._
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.collections.ObservableBuffer

@sfxml
class roomWindowController(
    private val listView: ListView[player]
) {
  
  def setPlayerList(players: Iterable[player]){
    listView.userData = players
  }
  
  def handleReady(event: ActionEvent){
    
  }
  
  val players = new ObservableBuffer[player]()
  
  listView.items = players
}