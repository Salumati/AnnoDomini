package model.gameComponent

import java.util.concurrent.Future

class Deckgenerator {

  def createDeckWithFutures(nuOfCards:Int = 60, startFrom: Int = 1): Deck = {
    
    /*
    val f1 = Future{createDeckWithFutures(nuOfCards = (nuOfCards/2), startFrom= (nuOfCards/2))}
    val f2 = Future{createDeckWithFutures((nuOfCards/2), startFrom = 1)}
    */
    
    createRandomDeck(nuOfCards)
  }

  def createRandomDeck(nuOfCards:Int = 30, startFrom: Int = 1): Deck ={
    val card = Card("Card No. " + (nuOfCards+startFrom), nuOfCards+startFrom)
    if(nuOfCards > 1) Deck(List(card)).addCard(createRandomDeck(nuOfCards-1))
    else Deck(List(card))
  }

  // futures: if cards is more than 30, split up deck generation to make it faster


  // TODO: add ability to create proper deck from file
  // TODO: add ability to choose deck
  // TODO: check if this would be better as an object rather than a class.
}