import scalafx.application.JFXApp
import scalafx.stage.Stage;
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Scene,Group}
import scalafx.scene.control.ListView
import scalafxml.core.FXMLLoader
import scalafxml.core.NoDependencyResolver
import scalafx.Includes._
import scalafx.application.Platform
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor._
import playerActor._
import scala.collection.mutable.ArrayBuffer
  
object clientApplication extends JFXApp {
 
  var currentRoom : Room = null;
  var gameBoardController : GameboardController = null;
  var roomWindowController:roomWindowController#Controller = null;
  //load fxml with fxml loader
  val loader = new FXMLLoader(null, NoDependencyResolver)
  
  //load the resource (fxml)
  val resource = getClass.getResourceAsStream("startingWindow.fxml")
  loader.load(resource)
  
  //get root node
  val rootNode = loader.getRoot[javafx.scene.layout.BorderPane]
  val startingWindowController = loader.getController[startingWindowController#Controller]
  
  //Actor System
  val system = ActorSystem("SnakeAndLadder")
  //val clientActor = system.actorOf(Props[playerActor]);
  val sa = system.actorOf(Props[serverActor]);
  val clientActor = system.actorOf(Props(new playerActor(startingWindowController,sa,"john")), "clientActor")
  
//  clientActor ! StartGame;
//  println("SHOUDL BE TURN");
//  clientActor ! PlayerTurn;
//  println("SHOULD BE MOVE");
//  clientActor ! Move();
  
  stage = new PrimaryStage(){
    title = "Snake and Ladder"
    scene = new Scene(width = 600, height = 400){
      root = rootNode
    }
    //proper shutdown
    onCloseRequest = handle {
      system.terminate() foreach{
        case _=>
        Platform.exit()
      }
    }
  }
  
  
  def replaceSceneContent(fxml: String){
   
   val loader = new FXMLLoader(null, NoDependencyResolver)
   val resource = getClass.getResourceAsStream(fxml)
   loader.load(resource)
   var page = loader.getRoot[javafx.scene.layout.BorderPane]
   var scene = stage.getScene();
   
   if (scene == null) {
       scene = new Scene(700, 450);
       stage.setScene(scene);
    }else {
       stage.getScene().setRoot(page);
    }
    stage.sizeToScene();
    roomWindowController = loader.getController[roomWindowController#Controller]
    //return page;
  }
  
  def startGameBoard(){
    val stage = new Stage()
    gameBoardController = new GameboardController();
    stage.setScene(gameBoardController.scene);
    stage.show()
  }
  
  def updatePlayerList( playerList : ArrayBuffer[player]){
    if(this.currentRoom != null){
      this.currentRoom.playerList = playerList;
    }
    roomWindowController.playerJoined()
    
  }
}
