package model.gameComponent

import model.gameComponent.{Deckgenerator, Player, Table, TableGenerator}
import org.scalatest.wordspec.AnyWordSpec

class TableGeneratorSpec extends AnyWordSpec(){
  "Table generator" when  {
    "created" should {
      val tb = TableGenerator()
      "have a create Table class" in{
        val table = tb.createTable()
        assert(table.players.length == 1)
      }

    }
  }
}
