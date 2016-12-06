import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import akka.pattern.ask
import scala.concurrent.duration._
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ TableView, TableColumn }
import scalafx.beans.property.{ StringProperty }
import scalafx.scene.control.Alert
import playerActor._;

@sfxml
class roomWindowController(
    private var playerListTable: TableView[player],
    private var playerNameColumn: TableColumn[player, String],
    private var playerStatusColumn: TableColumn[player, String]) {
  //  private var _dialogStage : Option[Stage] = None
  private var stage: Option[Stage] = None

  //  def dialogStage = _dialogStage.get
  //  def dialogStage_= (x : Stage)
  //  {
  //    _dialogStage = Some(x)
  //  }

  //var playerlist = new ObservableBuffer[player]()

  //playerListTable.items = playerlist //retrieve the container items
  playerJoined()
  
  def playerJoined() {
    var playerlist = new ObservableBuffer[player]()

    for (p <- clientApplication.currentRoom.playerList) {
      playerlist+=p
    }
    playerListTable.items = playerlist //retrieve the container ite
    playerNameColumn.cellValueFactory = (x) => new StringProperty(x.value.name)
    playerStatusColumn.cellValueFactory = (x) => {
      if(x.value.isReady){
        new StringProperty("ready");
      }else{
        new StringProperty("NOT");
      }
    }
    playerListTable.refresh()
  }

  def handleReady(event: ActionEvent) {
    clientApplication.clientActor ! Ready
    
    
  }
}