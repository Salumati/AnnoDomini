package model.gameComponent


import scala.util.{Failure, Success}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Deckgenerator {

  def createDeckWithFutures(nuOfCards:Int = 60, startFrom: Int = 1): Deck = {
    
    if (nuOfCards >= 30){
    val f1 = Future{createDeckWithFutures(nuOfCards = (nuOfCards/2), startFrom= (nuOfCards/2))}
    val f2 = Future{createDeckWithFutures((nuOfCards/2), startFrom = 1)}
    val deck1 = Await.result(f1, 20 nanos)
    val deck2 = Await.result(f2, 20 nanos)
    deck1.addCard(deck2)
    } else createRandomDeck(nuOfCards, startFrom)
  }

  def createRandomDeck(nuOfCards:Int = 30, startFrom: Int = 1): Deck ={
    val card = Card("Card No. " + (nuOfCards+startFrom), nuOfCards+startFrom)
    if(nuOfCards > 1) Deck(List(card)).addCard(createRandomDeck(nuOfCards-1))
    else Deck(List(card))
  }

  // TODO: add ability to create proper deck from file
  // TODO: add ability to choose deck
  // TODO: check if this would be better as an object rather than a class.
}