package model.gameComponent

import model.gameComponent.{Card, Deck, Deckgenerator}
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec{
  val deckGen:Deckgenerator = new Deckgenerator
  val deck:Deck = deckGen.createRandomDeck()
  val card:Card = Card("Card No. 0", 0)
  "A Deck" should{
    "Have a value cards, which is a List of Card used for the game" in {
      assert(deck.cards.isInstanceOf[List[Card]])
    }
    "Have value extraCards, which can be shuffeled into the deck when needed" in {
      assert(deck.extraCards.isInstanceOf[List[Card]])
      // test
    }
    "Have a function draw Card" in {
      assert(deck.drawFromDeck(4)._1.isInstanceOf[List[Card]])
      assert(deck.drawFromDeck(4)._2.isInstanceOf[Deck])
      assert(deck.drawFromDeck(4)._1.length == 4)
    }
    "Have a function addCard, " +
      "that works with a card, a list of cards and a whole deck" +
      "and adds the new card(s) on top of the current cars" in {
      assert(deck.addCard(card).cards.contains(card))
      assert(deck.addCard(card).deckHeadAsCard == card)
      assert(deck.addCard(card).numberOfCardsInDeck == deck.numberOfCardsInDeck + 1)
      assert(deck.addCard(deck).numberOfCardsInDeck == deck.numberOfCardsInDeck*2)
    }
    "Have a function shuffle, that shuffles the cards in the deck" in {
      assert(deck != deck.shuffle)
      assert(deck.cards.length == deck.shuffle.cards.length)
    }
    "Have a function deckHeadAsCard and deckHeadAsList, " +
      "wich return the fist card of a deck as a card or list of cards (containing only the first card)" in {
      assert(deck.deckHeadAsCard.isInstanceOf[Card])
      // assert(deck.deckHeadAsCard)
      // assert(deck.deckHeadAsList.isInsteanceOf[List[Card]])
    }
    "Have a function deckTail" in {
      assert(deck.deckTail.isInstanceOf[List[Card]])
      assert(deck.deckTail == deck.cards.tail)
    }
    "Have a function lenght" in{
      assert(deck.numberOfCardsInDeck == 10)
    }
    "Have a nice Stringrepresentation" in {
      assert(deck.toString.isInstanceOf[String])
      assert(deck.toString.startsWith("Deck:\n(" ))
      // Todo: change double nextLine to single nextLine
      assert(deck.toString.endsWith("1]\n)"))
    }
    "Can be copied" in {
      assert(deck.copy() == deck)
    }
    "Have a method equals" in {
      assert(deck.equals(deck))
    }
    "Be able expand itself with extraCards when needed" in{
      assert(deck.extraCards.length == 0)
      //assert(deck.addToExtraCards(card).extraCards.contains(card))

      var newDeck = deck.addToExtraCards(card)
    }

  }

}
