import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import akka.pattern.ask
import scala.concurrent.duration._
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableView, TableColumn}
import scalafx.beans.property.{StringProperty}
import scalafx.scene.control.Alert

@sfxml
class roomWindowController(
    private var playerListTable : TableView[player],
    private var playerNameColumn : TableColumn[player, String],
    private var playerStatusColumn : TableColumn[player, String]
) {
//  private var _dialogStage : Option[Stage] = None
  private var stage: Option[Stage] = None
  
//  def dialogStage = _dialogStage.get
//  def dialogStage_= (x : Stage)
//  {
//    _dialogStage = Some(x)
//  }
  
  var playerlist = new ObservableBuffer[player]()
  
  playerListTable.items = playerlist //retrieve the container items
  playerNameColumn.cellValueFactory = (x) => new StringProperty(x.value.name)
  
  def playerJoined(){
    //add player to table
    playerListTable.refresh()
  }
  
  def playerLeft(){
    //remove player left from table
    playerListTable.refresh()
  }
  
  def handleReady(event: ActionEvent){
    //player click ready update status
    val alert = new Alert(Alert.AlertType.Error){
              initOwner(stage getOrElse(clientApplication.stage))
              title = "Error"
              headerText = "Player name is empty!"
              contentText = "Please enter your name!"
            }.showAndWait()
  }
}