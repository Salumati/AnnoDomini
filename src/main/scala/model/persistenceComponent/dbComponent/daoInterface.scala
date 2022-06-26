package model.persistenceComponent.dbComponent

import model.Table

trait DaoInterface {

  def save(table: Table): Unit
  def load(): Table

}
