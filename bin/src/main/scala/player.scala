import akka.actor._;

case class player( name : String, ref : ActorRef) {
  var position = 1;
}