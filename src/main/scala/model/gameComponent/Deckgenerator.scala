package model

class Deckgenerator {
  def createRandomDeck(noOfCards:Int = 10): Deck ={
    val card = Card("Card No. " + noOfCards, noOfCards)
    if(noOfCards > 1) Deck(List(card)).addCard(createRandomDeck(noOfCards-1))
    else Deck(List(card))
  }
  // TODO: add ability to create proper deck from file
  // TODO: add ability to choose deck
}