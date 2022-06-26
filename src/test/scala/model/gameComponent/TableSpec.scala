package model.gameComponent

import model.gameComponent.{Card, Deck, TableGenerator, Table}
import org.scalatest.wordspec.AnyWordSpec

class TableSpec extends AnyWordSpec {
  val testCard1 = Card("Card No. 0", 0)
  val testCard2 = Card("Card No. 100", 100)
  val tg = TableGenerator(3, 40, 5)
  val table = tg.createTable
  "a Table:" when{
    "in general" should{
      "has a List of Playeres, that can play the game" in {
        assert(table.players.isInstanceOf[List[Player]])
        assert(table.players.length == 3)
        assert(table.currentPlayer == table.players.head)
        assert(table.showCurrentPlayer.contains("Player 3"))
      }
      "has a Deck" in {
        assert(table.deck.isInstanceOf[Deck])
      }
      "an Int punishmendCards, " +
        "that determines how many cards a player draws when punished" in {
        assert(table.numberOfPunishmentCards == 3)
      }
      "has a good Stringrepresentations" in {
        assert(table.showTable.contains("The board:\n"))
      }
    }
    "when new" should {
      "have one Card in itself" in {
        assert(table.cardsOnTable.length == 1)
      }
      "not have a player that won" in {
        assert(table.playerWon == false)
      }
    }
    "when a player places a card" should{
      "get the next player" in{
        assert(table.currentPlayer != table.playerPlacesCard(1, 0).currentPlayer)
      }
    }
    "when a player doubts" should{
      "the doubting player should be punished, if the order of cards on the table was correct" in{
        var newTable = table.copy(cardsOnTable = List(testCard1, testCard2))
        assert(table.currentPlayer == newTable.currentPlayer)
        assert(table.currentPlayer != newTable.playerDoubtsCards.currentPlayer)
        assert(table.currentPlayer.name == newTable.playerDoubtsCards.previousPlayer.name)
        assert(table.currentPlayer.checkNumOfCards ==
          (newTable.playerDoubtsCards.previousPlayer.checkNumOfCards - newTable.numberOfPunishmentCards))
      }
      "the previous player should be punished, if the order of cards on the table was incorrect" in{
        var newTable = table.copy(cardsOnTable = List(testCard2, testCard1))
        assert(table.previousPlayer == newTable.previousPlayer)
        assert(table.previousPlayer.name == newTable.playerDoubtsCards.previousPlayer.name)
        assert(table.currentPlayer == newTable.playerDoubtsCards.currentPlayer)
        assert(table.previousPlayer.checkNumOfCards ==
          (newTable.playerDoubtsCards.previousPlayer.checkNumOfCards - (newTable.numberOfPunishmentCards+1)))
      }
    }
    "if a player has no more cards" should{
      "declare the player the winner" in{
        val winningPlayer = Player("Winner", List(testCard1))
        val newTable = table.copy(players = winningPlayer::table.players)
        assert(!newTable.playerWon)
        assert(newTable.playerPlacesCard(0, 0).playerWon)
      }
    }
  }

}
