package model.gameComponent

case class Player(name: String = "Player", hand: List[Card]) {
  def showHand: String = this.toString()
  def checkNumOfCards: Int = hand.length

  def getCard(n: Int): (Card, Player) = {
    (hand(handleBadNumber(n)), copy(hand = hand.diff(List(hand(handleBadNumber(n))))))
  }
  def handleBadNumber(i: Int): Int = {
    i match {
      case x if (x >= hand.size) => hand.size - 1
      case x if (x < 0) => 0
      case _ => i
    }
  }

  def giveCards(newCards: List[Card]): Player = copy(hand = hand ::: newCards)
  def hasWon: Boolean = hand.isEmpty
  override def toString: String = name + ":\n" + hand.toString().replaceAll("List", "").replaceAll(",", "")
  // TODO: check if one regex can be used for proper replacement
}
