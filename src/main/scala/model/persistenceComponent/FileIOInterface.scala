package model.persistenceComponent

import model.Table

trait FileIOInterface {
  def save(table:Table): Unit
  def load: Table
}
