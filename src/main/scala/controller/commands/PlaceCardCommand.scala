package controller.commands

import controller.Controller
import model.gameComponent.Table

case class PlaceCardCommand(thisCard:Int, placeAt:Int, controller:Controller, oldTable:Table) extends util.Command{
  override def doStep: Unit = controller.table = controller.table.playerPlacesCard(thisCard, placeAt)
  override def undoStep: Unit = controller.table = oldTable
  override def redoStep: Unit = controller.table = controller.table.playerPlacesCard(thisCard, placeAt)
}
