package model.gameComponent

import model.gameComponent.{Deck, Deckgenerator}
import org.scalatest.wordspec.AnyWordSpec

class DeckGeneratorSpeck extends AnyWordSpec{
  "A Deckgenerator" should{
    "have a Method createRandomDeck" in{
      val deckGen = new Deckgenerator
      assert(deckGen.createRandomDeck().isInstanceOf[Deck])
      assert(deckGen.createRandomDeck().numberOfCardsInDeck == 10)
    }
  }
}
