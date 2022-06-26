package controller


import model.{Card, Table, TableGenerator}
import model.persistenceComponent.XMLImpl.FileIOAsXML
// TODO: properly use google guice here
// import model.persistenceComponent.XMLImpl.FileIO
import util.{Observable, UndoManager}
import controller.commands.{DoubtCommand, PlaceCardCommand}

/*
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route


import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpMethods
import akka.protobufv3.internal.compiler.PluginProtos.CodeGeneratorResponse.File
import akka.http.scaladsl.model.MediaTypes
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import akka.http.scaladsl.unmarshalling.Unmarshal
*/

class Controller(var table: Table) extends Observable{


//AKKA STUFF (REST)
  val fileIoHost: String = "localhost"
  val fileIoPort: Int = 8081
  //val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  //given ActorSystem[Any] = system
  // val executionContext: ExecutionContextExecutor = system.executionContext
  //given ExecutionContextExecutor = executionContext


  val undoManager = new UndoManager
  val fileIOAsXML = new FileIOAsXML


  def createTestTable(noOfPlayers:Int): Unit = {
    val tb = new TableGenerator
    table = tb.createTable(noOfPlayers, 40)
    notifyObservers()
  }

  def tableToString: String = table.showTable + table.showCurrentPlayer

  def placeCard(card:Int, place:Int): Unit ={
    undoManager.doStep(PlaceCardCommand(card, place, this, table.copy()))
    notifyObservers()
  }

  def doubt():Unit = {
    undoManager.doStep(DoubtCommand(this, table.copy()))
    notifyObservers()
  }

  def showAllPlayers(): String = table.showAllPlayers
  def confirmWinner: String = if (table.playerWon) {"congratulations, player: " + table.previousPlayer + " has won!"} else {""}
  def getCard(index:Int): Card = table.takePlayerCard(index)._1

  def undo(): Unit ={
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit ={
    undoManager.redoStep
    notifyObservers()
  }

  def saveGame(): Unit = fileIOAsXML.save(table)

  def loadGame(): Unit= {
    table = fileIOAsXML.load
    notifyObservers()
  }
  /*
  def saveGameViaRestAsXML(): Unit = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val tableAsXml = fileIOAsXML.tableToXML(table)
    val xmlAsString = tableAsXml.toString

    println("xmlAsString:\n")
    println(xmlAsString)

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$fileIoHost:$fileIoPort/fileIO/xml/save",
        entity = HttpEntity(ContentTypes.`text/xml(UTF-8)`, xmlAsString)
      ))

  }
*/

/*
  // nur zum Abschauen
  def loadGameViaRestAsXML(): Future[Boolean] = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = s"http://$fileIoHost:$fileIoPort/fileIO/xml/load"
      ))

    responseFuture
      .onComplete {

        case Success(value) =>

          println("%%%%%%%% jetzt in load on complete Success(value) %%%%%%%%")

          val tableAsString = Unmarshal(value.entity).to[String]
          // ergibt folgendes Element:  //val tableAsString = value.entity.toString   //HttpEntity.Strict(text/xml; charset=UTF-8,2363 bytes total)

          tableAsString.onComplete {
            case Success(value) =>
              println("tableAsString:")
              println(tableAsString)
              println("value:")
              println(value)
              val nowAsXml = scala.xml.XML.loadString(value.toString)
              println("nowAsXml:")
              println(nowAsXml.toString)

              table = fileIOAsXML.tableFromXML(nowAsXml)

              println("table:")
              println(table)
              notifyObservers()
              return Future(true)
            case Failure(exception) =>
              return Future(false)
          }

        case Failure(exception) =>
          println("%%%%%%%% jetzt in load  -  failure fall 1 %%%%%%%%")
          return Future(false)
      }
    return Future(false)
  }
  */


}