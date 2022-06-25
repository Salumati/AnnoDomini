package model

case class TableGenerator(noOfPlayers: Int = 1, noOfCards: Int = 30, CardsPerPlayer: Int = 5) {
  // TODO: change this to a parameterless class, put the parameters into the createTable method
  val deckGen = new Deckgenerator

  def createTable: Table = {
    val deck = deckGen.createRandomDeck(noOfCards).shuffle
    val players = genPlayers(deck, noOfPlayers)
    Table(
      players = players,
      cardsOnTable = List(deck.drawFromDeck(noOfPlayers * CardsPerPlayer)._2.deckHeadAsCard),
      deck = deck.drawFromDeck(noOfPlayers * CardsPerPlayer)._2)
    // TODO: make a version of this, that isn'T as confusing
  }

  def genPlayers(deck: Deck, n: Int = noOfPlayers): List[Player] = {
    if (n <= 1) List(Player("Player " + n, deck.drawFromDeck(CardsPerPlayer)._1))
    else List(Player("Player " + n, deck.drawFromDeck(CardsPerPlayer)._1)) ::: genPlayers(deck.drawFromDeck(CardsPerPlayer)._2, n - 1)
  }
}
