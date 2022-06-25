package model
/*
Table provides the main functionality for the game.
It is the table the game gets played on.
As such it manages the players, the cards placed on th e table and the deck.
Game rules:
The current Player gets to either place a card from their hand on the table or to doubt the order of the currently played cards.
If the player chooses to place a card they decide where to place it and the turn goes to the next player.
If the player doubts the card order the order of cards on the table gets checked and the table gets reset.
If the cards are in order, the current player gets punished for wrongfully doubting the cards and the turn goes to the next player.
If the cards are not in order, the previous player gets punished for trusting the fals cardorder and the current player gets to continue their turn.
On a table reset, all cards currently on the table get put to the extra cards in the deck and a single new card gets placed on the table.
*/

case class Table(players: List[Player], cardsOnTable: List[Card], deck: Deck, numberOfPunishmentCards: Int = 3) {
  
  def showTable: String = "The board:\n" + cardsOnTable.toString() + "\n"
  def showAllPlayers: String = players.toString() // might not be needed, the other players cards should not be visible
  def showCurrentPlayer: String = currentPlayer.toString
  def showStatus: String = showTable + players.map(p => p.name + ": (" + p.checkNumOfCards + ")\n").toString()
  def currentPlayer: Player = players.head
  def previousPlayer: Player = players.last
  def getNumOfPlayers: Int = players.length
  def playerWon: Boolean = previousPlayer.hasWon
  def takePlayerCard(index: Int): (Card, Player) = currentPlayer.getCard(index)
  def placeCard(card: Card)(position: Int): List[Card] = cardsOnTable.splitAt(position)._1 ::: card :: cardsOnTable.splitAt(position)._2

  def playerDrawsCard(player: Player, numOfCards: Int = 1): (Player, Deck) =
    (player.giveCards(deck.drawFromDeck(numOfCards)._1), deck.drawFromDeck(numOfCards)._2)

  def playerPlacesCard(takeThisCard: Int, placeCardAt: Int): Table = {
    val playerCard = takePlayerCard(takeThisCard)._1
    val player = takePlayerCard(takeThisCard)._2
    copy(players = getNextPlayer(player), cardsOnTable = placeCard(playerCard)(placeCardAt))
  }

  // TODO: make compatible with 1 Player session
  def getNextPlayer(player: Player = currentPlayer, keepCurrentPlayer: Boolean = false): List[Player] = {
    keepCurrentPlayer match
      case false => players.tail ::: List(player)
      case true => changePrevPlayer(player)
  }

  def changePrevPlayer(changedPlayer: Player): List[Player] = {
    currentPlayer :: players.tail.dropRight(1) ::: List(changedPlayer)
  }

  def allCardsInOrder: Boolean = {
    val sortedList = cardsOnTable.sortWith(_.year < _.year)
    // TODO: this can probably be done better ith a fold left function, make a qucker version of this.
    cardsOnTable == sortedList
  }

  def playerDoubtsCards: Table = {
    // TODO: change to match case and put stuff in different functions for better overview
    allCardsInOrder match
      case true => punishPlayer(currentPlayer, numberOfPunishmentCards, false)
      case false => punishPlayer(previousPlayer, numberOfPunishmentCards +1, true)
  }

  def punishPlayer(player: Player, numOfCards: Int, keepCurrentPlayer: Boolean = false): Table = {
    val changedPlayer = playerDrawsCard(player, numOfCards)._1
    val newDeck = playerDrawsCard(player, numOfCards)._2
    copy(
      players = getNextPlayer(changedPlayer, keepCurrentPlayer),
      cardsOnTable = newDeck.deckHeadAsList,
      newDeck.copy(extraCards = newDeck.addToExtraCards(cardsOnTable))
    )

  }
}
