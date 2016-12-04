import scalafx.application.JFXApp
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
import scalafx.scene.shape.{Line, Circle}
import scala.collection.mutable.ArrayBuffer
import scalafx.scene.image.{ImageView,Image}

object Application extends JFXApp {
  
  val elements = ArrayBuffer[Circle]()
  
  //create different color of plates for player
  generatePlayerPlate("red")
  generatePlayerPlate("blue")
  generatePlayerPlate("green")
  generatePlayerPlate("yellow")
  
  var imv = new ImageView()
  val image2 = new Image(getClass.getResourceAsStream("gameboard.gif"));
  imv.setImage(image2);
  
  stage = new PrimaryStage(){
    title = "Snake and Ladder"
    scene = new Scene(width = 800, height = 600){
      root = new Group{
        children = imv
      }
    }
  }
  
  def generatePlayerPlate(color: String){
    var circle = new Circle
    circle.radius = 1
    circle.fill = color
    elements += circle
  }
}