package model.gameComponent

import model.gameComponent.{Deckgenerator, Player, Table, TableGenerator}
import org.scalatest.wordspec.AnyWordSpec

class TableGeneratorSpec extends AnyWordSpec(){
  "Table generator" when  {
    "new" should {
      val tb = TableGenerator(2, 40)
      "have a number of Players " in {
        assert(tb.noOfPlayers == 2)
      }
      "have a number of Cards " in {
        assert(tb.noOfCards == 40)
      }
      "have a Deckgenerator" in {
        assert(tb.deckGen.isInstanceOf[Deckgenerator])
        //assert(tb.deckGen)
      }
      "have a method generate Players" in {
        val deck = tb.deckGen.createRandomDeck(40)
        val playerList = tb.genPlayers(deck, 2)
        assert(playerList.isInstanceOf[List[Player]])
        assert(playerList.length == 2)

      }
      "have a method createTable" in {
        val table = tb.createTable
        assert(table.isInstanceOf[Table])
        assert(table.players.length == 2)
        assert(table.deck.numberOfCardsInDeck == 29)
      }
    }
  }
}
