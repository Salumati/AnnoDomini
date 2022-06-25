package model.persistenceComponent.xml

import model.Table

trait FileIOInterface {
  def save(table:Table): Unit
  def load: Table
}
