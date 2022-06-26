package model.persistenceComponent.dbComponent

import model.gameComponent.Table

trait DaoInterface {

  def save(table: Table): Unit
  def load(): Table

}
