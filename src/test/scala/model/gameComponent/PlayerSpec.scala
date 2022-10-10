package model.gameComponent

import model.gameComponent.{Card, Deck, Deckgenerator, Player}
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec{
  val deckGen:Deckgenerator = new Deckgenerator
  val deck:Deck = deckGen.createRandomDeck()
  val hand:List[Card] = deck.drawFromDeck(5)._1
  val player:Player = Player("Player", hand)
  "A Player" when{
    "new" should {
      "have a name" in {
        assert(player.name.isInstanceOf[String])
        assert(player.name == "Player")
      }
      "have a List of Cards named hand" in{
        assert(player.hand.isInstanceOf[List[Card]])
        assert(player.hand == hand)
        assert(player.hand.length == 5)

        assert(player.showHand.isInstanceOf[String])
        assert(player.showHand.contains("[Card No. 10]\n"))

        assert(player.checkNumOfCards == 5)
      }
      "cannot yet have won" in {
        assert(!player.hasWon )
      }
      "has a nice Stringrepresentation " in {
        assert(player.toString.startsWith("Player:\n"))
        assert(player.toString.endsWith("[Card No. 6]\n)"))
      }
    }
    "in general" should {
      "have a method getCard" in {
        val takenCard = player.getCard(3)._1
        val changedPlayer = player.getCard(3)._2
        assert(takenCard.isInstanceOf[Card])
        assert(takenCard == deck.drawFromDeck(4)._1(3))
        assert(player.checkNumOfCards > changedPlayer.checkNumOfCards)
        assert(changedPlayer.checkNumOfCards == 4)

      }
      "have a method giveCards" in{
        val card1 = Card("a Card", 44)
        val card2 = Card("another Card", 42)

        assert(!player.hand.contains(List(card1, card2)))

        val changedPlayer = player.giveCards(List(card1, card2))
        assert(changedPlayer.isInstanceOf[Player])
        assert(changedPlayer.hand.contains(card1))
        assert(changedPlayer.hand.contains(card2))
        assert(changedPlayer.checkNumOfCards == 7)
      }
    }
    "has won" should {
      val winingPlayer = Player("Winner", List():List[Card])
      "have an empty hand" in{
        assert(winingPlayer.checkNumOfCards == 0)
      }
      "is the winner:" in{
        assert(winingPlayer.hasWon)
      }
    }
  }
}
