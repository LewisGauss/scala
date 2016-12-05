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

class GameboardController {
  val rollButton = new Button("Roll Dice")
  rollButton.disable = true
  rollButton.layoutX = 650
  rollButton.layoutY = 500
  val rec1 = new Rectangle {
    width = 30
    height = 30
    fill = LightGreen
    x = 12
    y = 560
  }

  def getCoordinates(position: Int): Coor = {
    val x = ((position - 1) % 10) * 60
    val y = 560 - (Math.floor((position - 1) / 10) * 60)
    return new Coor(x, y)
  }

  var counter = 1;

  val scene = new Scene(760, 600) {
    var imv = new ImageView()

    val image2 = new Image(getClass().getResourceAsStream("gameboard.gif"))
    imv.setImage(image2)

    val diceNum = new Label("1/2/3/4/5/6")
    diceNum.layoutX = 650
    diceNum.layoutY = 550

    var playersMap = new HashMap[player, Label]();
    var labelY = 50;

    val rootPane = new BorderPane
    val mainPane = new AnchorPane
    rootPane.center = mainPane
    root = rootPane

    //mainPane.items ++= List(imv, rollButton, diceNum, player1, player2, player3, player4)
    mainPane.getChildren().add(imv)
    mainPane.getChildren().add(rollButton)
    mainPane.getChildren().add(diceNum)
    for (p <- clientApplication.currentRoom.playerList) {
      val playerLabel = new Label(p.name);
      playerLabel.layoutX = 650;
      playerLabel.layoutY = labelY
      playersMap.put(p, playerLabel);
      labelY += 30;
      mainPane.getChildren().add(playerLabel);
    }

    mainPane.getChildren().add(rec1)
    //content = List(imv, rollButton, diceNum, player1, player2, player3, player4)

    rollButton.onMouseClicked = (e: Event) => {
      println("Clicked")
      clientApplication.clientActor ! Play
    }
  }

  def myTurn() {
    rollButton.disable = false;
  }

  def updateUI() {

  }

}

class Coor(val x: Double, val y: Double)