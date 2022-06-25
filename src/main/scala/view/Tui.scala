package view

import controller.Controller
import util.Observer

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

def NumberOfPlayers(num: Int) : 2 | 3 | 4 ={
  num match
    case 2 => 2
    case 3 => 3
    case 4 => 4
    case _ => correctNumber(num)
}
def correctNumber(num: Int) : 2 | 3 | 4 = {
  var returnNum = 3
  if  (num <= 2)  returnNum = 2
  else if (num >= 4) returnNum = 4
  returnNum match
    case 2 => 2
    case 4 => 4
    case _ => 3
  // TODO: check if this can be simplyfied with higher functions
}

class Tui(controller: Controller) extends Observer{

  controller.add(this)

  def startGame(): Unit ={
    println("Welcome to Anno Domini!")
    // show rules?
    // change between play state and testing state

    // select how many Players
    println("how many players? (2, 3 or 4)")
    val numOfPlayers: 2 | 3 | 4 = NumberOfPlayers(handleIntegerInput())
    controller.createTestTable(numOfPlayers)
    // if only one player: switch into Singleplayer mode

    // start normal Inputprocess
    handlePlayerInput()
  }
  
  def handlePlayerInput(): Unit ={
    giveOptions()
    val input = readLine()
    println(s"input is: $input")
    processInputLine(input)
  }

  def processInputLine(input: String): Unit ={
    input match {
      case "q" => println("End game.")
      case "d" => doAMove(controller.doubt())
      case "p" => doAMove(placeCard())
      case "a" => doAMove(showAllPlayers())
      case "u" => doAMove(controller.undo())
      case "r" => doAMove(controller.redo())
      /*
      case "s" => doAMove(saveGame())
      case "l" => doAMove(loadGame())
      
      case "rl" => doAMove(loadGameViaRest())
      case "rs" => doAMove(saveGameViaRest())
      */
      case _ => wrongInput()
    }
  }

  private def doAMove(function: Unit): Unit ={
    function
    //confirmWinner()
    handlePlayerInput()
    // TODO: This could be handled with a Command Pattern instead, might be better?
  }

  def giveOptions(): Unit = {
    println("what will you do?" +
      "\n p = place card " +
      "\n d = doubt" +
      "\n u = undo recent step" +
      "\n r = redo"+
      "\n s = save game" +
      "\n l = load game" +
      "\n rs = save game the REST way" +
      "\n rl = load game the REST way" +      
      "\n q = quit game" +
      "\n a = look at all Players")
  }

  def placeCard(): Unit = {
    println(controller.tableToString)
    println("which card?")
    val cardInput = handleIntegerInput()

    println("you selected: " +
      controller.getCard(cardInput).toString +
      "where should it be placed?")
    val placeInput = handleIntegerInput()
    controller.placeCard(cardInput, placeInput)
  }

  def handleIntegerInput(): Int ={
    val input = readLine()
    myToInt(input) match {
      case Success(n) => n
      case Failure(s) => {
        println("Please input an Integer!")
        handleIntegerInput()
      }
    }
  }
  def myToInt(string: String): Try[Int] = Try(Integer.parseInt(string.trim))

  def wrongInput(): Unit = {
    println("Didn't understand your input, please type again.")
    handlePlayerInput()
  }

  def showAllPlayers(): Unit = {
    println(controller.showAllPlayers())
  }
  /*
  def saveGame(): Unit ={
    controller.saveGame()
    println("saved game")
  }
  def loadGame(): Unit={
    controller.loadGame()
    println("game loaded")
  }
  def saveGameViaRest(): Unit ={
    println("saving game the REST way...")
    controller.saveGameViaRestAsXML()
  }

  def loadGameViaRest(): Unit={
    println("loading game the REST way...")
    controller.loadGameViaRestAsXML()
  }
  */
  def confirmWinner(): Unit = {
    println(controller.confirmWinner)
  }
  
  override def update(): Unit = println(controller.tableToString)


}

