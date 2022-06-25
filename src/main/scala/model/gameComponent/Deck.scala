package model

import scala.util.Random

case class Deck(cards: List[Card] = Nil, extraCards: List[Card] = Nil) {
  /*
  cards:      list of cards currently in the deck
  extraCards: a list of extra cards, that are currently not in the deck but can be put into the deck when it runs out of cards.
              during the game, the extra cards will be crads that have been discarded.
  */
  def drawFromDeck(n: Int): (List[Card], Deck) = {
    n match
      case x if (deckHasEnoughCards(x)) => (cards.splitAt(n)._1, copy(cards = cards.splitAt(n)._2))
      case _ => createDeckFromExtraCards().drawFromDeck(n)
    // Problem: if there aren't enough extraCards this will end in a loop
  }

  def deckHasEnoughCards(neededCards: Int): Boolean = neededCards <= numberOfCardsInDeck
  def createDeckFromExtraCards(): Deck = addCard(Deck(extraCards).shuffle)
  // TODO: let this recognize, if the Cards in extraCards aren't enough to fulfill the demand for extra cards
  def addCard(c: Card): Deck = Deck(c :: cards)
  def addCard(d: Deck): Deck = Deck(cards ::: d.cards)
  def addCard(l: List[Card]): Deck = Deck(cards ::: l)
  def addToExtraCards(c: Card): List[Card] = c :: extraCards
  def addToExtraCards(l: List[Card]): List[Card] = l ::: extraCards
  def shuffle: Deck = Deck(Random.shuffle(cards))
  def deckHeadAsList: List[Card] = drawFromDeck(1)._1
  def deckHeadAsCard: Card = drawFromDeck(1)._1.head
  def deckTail: List[Card] = cards.tail
  // these are for easing the creation of table class objects:
  def numberOfCardsInDeck: Int = cards.length
  def numberOfCardsInExtraDeck: Int = extraCards.length
  override def toString: String = cards.map(c => c.toString).toString().replaceAll("List", "Deck:\n").replaceAll(",", "")
  // TODO: find out how to replace the '(' character to make a nice toString methos, for some reason the regex in replaceAll doesn't accept '/('
  // TODO: put the '/n' escpae character into this toString method, so it doesn't have to be in the Card class toString method.
}
