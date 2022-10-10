package controller.commands
import controller.Controller
import model.gameComponent.Table

case class DoubtCommand(controller:Controller, oldTable:Table) extends util.Command{
  override def doStep: Unit = controller.table = controller.table.playerDoubtsCards
  override def undoStep: Unit = controller.table = oldTable
  override def redoStep: Unit = controller.table = controller.table.playerDoubtsCards
}
