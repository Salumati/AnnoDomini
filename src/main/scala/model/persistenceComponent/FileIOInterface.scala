package model.persistenceComponent

import model.gameComponent.Table

trait FileIOInterface {
  def save(table:Table): Unit
  def load: Table
}
