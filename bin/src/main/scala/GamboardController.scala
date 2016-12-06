import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.control.Button.sfxButton2jfx
import scalafx.scene.control.Label
import scalafx.scene.control.Label.sfxLabel2jfx
import scalafx.scene.image.Image
import scalafx.scene.image.Image.sfxImage2jfx
import scalafx.scene.image.ImageView
import scalafx.scene.image.ImageView.sfxImageView2jfx
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.AnchorPane.sfxAnchorPane2jfx
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.event.Event
import scala.collection.mutable.ArrayBuffer;
import scalafx.event.EventHandler;
import scala.collection.mutable.HashMap
import playerActor._;

import scalafx.scene.control.Alert
class GameboardController {
  val rollButton = new Button("Roll Dice")
  rollButton.disable = true
  rollButton.layoutX = 650
  rollButton.layoutY = 500
  val color = Array(
    Blue,
    Green,
    Orange,
    Pink);
  def getCoordinates(position: Int): Coor = {
    
    var x: Double = 0;
    if (position % 10 == 0) {
        x = if(((position /10) % 2) == 0) 0 else 540
    } else {
      if (Math.floor(position / 10) % 2 == 0) {
        x = ((position - 1) % 10) * 60
      } else {
        x = 560 - (((position - 1) % 10) * 60)
      }
    }
    val y = 560 - (Math.floor((position - 1) / 10) * 60)
    return new Coor(x, y)
  }

  var playersMap = new HashMap[player, Rectangle]();

  var counter = 1;

  val scene = new Scene(760, 600) {
    var imv = new ImageView()

    val image2 = new Image(getClass().getResourceAsStream("gameboard.gif"))
    imv.setImage(image2)

    val diceNum = new Label("1/2/3/4/5/6")
    diceNum.layoutX = 650
    diceNum.layoutY = 550

    var labelY = 50;

    val rootPane = new BorderPane
    val mainPane = new AnchorPane
    rootPane.center = mainPane
    root = rootPane

    //mainPane.items ++= List(imv, rollButton, diceNum, player1, player2, player3, player4)
    mainPane.getChildren().add(imv)
    mainPane.getChildren().add(rollButton)
    mainPane.getChildren().add(diceNum)
    var i = 0
    for (p <- clientApplication.currentRoom.playerList) {
      val playerLabel = new Label(p.name);
      val rec1 = new Rectangle {
        width = 30
        height = 30
        fill = color(i)
        x = 12
        y = 560
      }
      playerLabel.layoutX = 650;
      playerLabel.layoutY = labelY
      playerLabel.setTextFill(color(i))
      i += 1
      playersMap.put(p, rec1);
      labelY += 30;
      mainPane.getChildren().add(playerLabel);

      mainPane.getChildren().add(rec1)
    }

    //content = List(imv, rollButton, diceNum, player1, player2, player3, player4)

    rollButton.onMouseClicked = (e: Event) => {

      clientApplication.clientActor ! Play
      rollButton.disable = true;
      
    }
  }

  def myTurn() {
    rollButton.disable = false;
  }

  def updateUI() {
    for (p <- clientApplication.currentRoom.playerList) {
      val pos = getCoordinates(p.position)
      val rec = playersMap(p);
      rec.relocate(pos.x, pos.y)
    }
  }
  
  def Win(name : String){
    
    val alert = new Alert(Alert.AlertType.Error) {
          initOwner( (clientApplication.stage))
          title = "End"
          headerText = "End"
          contentText = name + " has won the game"
        }.showAndWait()
  }

}

class Coor(val x: Double, val y: Double)