package model.persistenceComponent.dbComponent.slickImpl

import slick.jdbc.MySQLProfile.api._

class CardTable(tag: Tag)
  extends Table[(Int, Int, String, Int)](tag, "CARDS") {
  def card_id = column[Int]("CARD_ID", O.PrimaryKey, O.AutoInc)
  def card_year = column[Int]("CARD_YEAR")
  def card_text = column[String]("CARD_NAME")
  def card_owner = column[Int]("CARD_OWNER_ID")

  def * = (card_id, card_year, card_text, card_owner)
}

class PlayerTable(tag: Tag)
  extends Table[(Int, String, Int)](tag, "PLAYERS"){
  def player_id = column[Int]("PLAYER_ID", O.PrimaryKey, O.AutoInc)
  def player_name = column[String]("PLAYER_NAME")
  def game_id = column[Int]("GAME_ID")

  def * = (player_id, player_name, game_id)
}

class GameTable(tag: Tag)
  extends Table[(Int, Int)](tag, "GAMES"){
  def game_id = column[Int]("GAME_ID", O.PrimaryKey)
  def deck_id = column[Int]("DECK_ID")

  def * = (game_id, deck_id)
}

class DeckTable(tag: Tag)
  extends Table[(Int)](tag, "DECK"){
  def deck_id = column[Int]("DECK_ID", O.PrimaryKey)

  def * = (deck_id)
}