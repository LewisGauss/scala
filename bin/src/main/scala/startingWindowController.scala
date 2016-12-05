import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.TextField
import scalafx.scene.control.Alert
import scalafx.stage.Stage
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scalafx.application.Platform
import scalafxml.core.FXMLLoader
import scalafxml.core.NoDependencyResolver
import scalafx.scene.{Scene,Parent}
import clientApplication._
import scalafx.scene.image.{ImageView,Image}

@sfxml
class startingWindowController(inputNameField: TextField,startingImage: ImageView){
  
  import playerActor._
  import serverActor._
  
  val image = new Image(getClass.getResourceAsStream("starting.png"));
  startingImage.setImage(image)
  private var stage: Option[Stage] = None
  
  
  def handleConnect(event: ActionEvent){
    val input = inputNameField.text.value
    input.trim
    if(input != ""){
      //connect user to a game room when user enter a valid name
      implicit val timeout: Timeout = Timeout(30 seconds)
      clientApplication.clientActor ? ConnectToServer foreach{
        case RegistrationSuccess =>
          Platform.runLater(clientApplication.replaceSceneContent("roomWindow.fxml"))
      }
    }else{
      val alert = new Alert(Alert.AlertType.Error){
              initOwner(stage getOrElse(clientApplication.stage))
              title = "Error"
              headerText = "Player name is empty!"
              contentText = "Please enter your name!"
            }.showAndWait()
     }
   }
 }