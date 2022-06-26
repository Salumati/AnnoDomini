//import java.util.Scanner

import controller.Controller
import model.gameComponent.TableGenerator
import view.Tui

import scala.io.StdIn.readLine



// general things to do:
// Missing functionality form SE
// reintegrade Continuous deployment with Travis (need new Travis Account) (HA 6)
// add Design Patterns (HA 7)
// add Undo and Redo (HA 8)
// add Gui (HA 9)
// Add Interfaces (HA 10 )
// do Dependency Injection (HA 11)
// Add method to save game (HA 12 - File IO)
// Add Docker (HA 13)

// TODO: add method to check numbers of Players
// TODO: add method to view all players at once
// TODO: add better Input errorhandling


object AnnoDomini {

  val controller = new Controller(TableGenerator().createTable())
  val tui = new Tui(controller)

  // val scanner = new Scanner(System.in)

  def main(args:Array[String]):Unit =
    tui.startGame()
}
