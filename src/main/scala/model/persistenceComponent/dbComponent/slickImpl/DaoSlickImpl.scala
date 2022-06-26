package model.persistenceComponent.dbComponent.slickImpl

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import slick.dbio.{DBIO, DBIOAction}
import slick.jdbc.MySQLProfile.api._

import model.persistenceComponent.dbComponent.DaoInterface
import model.persistenceComponent.XMLImpl.FileIOAsXML
import model.{Card, Table, Deck, Player, TableGenerator}


class DaoSlickImpl extends DaoInterface{
  
    /*
  val dbUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "cah") + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"
  val dbUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val dbPassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "1234")


  val db = Database.forURL(
    url = dbUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )
  */


  override def save(table: Table): Unit = {
    println("saving game in db")
  }

  override def load(): Table = {
    println("loading game form db")
    val db = Database.forConfig("mysqldb")
    try{
        val games = TableQuery[GameTable]
    } finally db.close

    val tg = new TableGenerator
    tg.createTable()
  }
  





/*
  val gameID = 100
  val deckID = 200

  val cardTable = TableQuery[CardTable]
  val deckTable = TableQuery[DeckTable]
  val gameTable = TableQuery[GameTable]
  val playerTable = TableQuery[PlayerTable]

  override def save(game: String): Unit =
  {
    println("Saving game in MySQL")
    val db = Database.forConfig("mysqldb")
    try{
      println("attempting to save mockdata in db:")
      val setup = DBIO.seq((deckTable.schema ++ gameTable.schema ++ cardTable.schema ++ playerTable.schema).createIfNotExists)

      val setupFuture = db.run(setup)
      setupFuture.onComplete {
        case Success(s) => println(s"created Database")
        case Failure(fa) => println(s"failed to insert values. error: $fa")
      }
      Await.ready(setupFuture, Duration(100, MILLISECONDS))

      val gameAsXML = scala.xml.XML.loadString(game)
      val deck = deckFromXML(gameAsXML)
      val players = playerListFromXML(gameAsXML)
      val tableCards = cardListFromXML(gameAsXML)

/*
      // save the deck and it's cards
      val insertDeckActionFuture = db.run(gameTable += (gameID, deckID))
      val insertDeckCardsActionFuture = db.run(for (card <- deck) do yield cardTable += (card._1, card._2, deckID))
      // save all the cards on the table
      val insertTableCardsActionFuture = db.run(for (card <- tableCards) do yield cardTable += (card._1, card._2, gameID))
      // save the players
      val insertPlayersAction = DBIO.seq{
        for (player <- players)
          do
          yield playerTable += (player._1, gameID)
          for (card <- player._2) do yield cardTable += (card._1, card._2)
      }
      val insertPlayerFutur = db.run(insertPlayersAction)

*/
/*

      Await.ready(insertDeckActionFuture)
      Await.ready(insertTableCardsActionFuture)
      Await.ready(insertTableCardsActionFuture)
      Await.ready(insertPlayerFutur)
*/

    }finally db.close()

    }

  override def load: Option[String] =  {
    println("loading game from mysql db")
    val db = Database.forConfig("mysqldb")

    try{

        println("blabla")
        Option[String]("bla")
        /*
      val gameQuery = gameTable.filter(_.game_id = gameID)

      val players = db.run(players.result).map(_.foreach{
        case (player_id, player_name, game_id) =>
          (player_id, player_name, game_id)})

      val cards = db.run(cards.result).map(_.foreach{
        case (card_id, card_year, card_text, card_owner) =>
          (card_id, card_year, card_text, card_owner)})

      // get the cards on the game table
        */

    }finally {
      db.close()
    }

  }

  // xml reading classes:
  def cardFromXML(xml: scala.xml.Node): (text:String, year:Int) = {
    val year = (xml \ "year").text.toInt
    val text = (xml \ "text").text
    (year, text)
  }

  def cardListFromXML(xml: scala.xml.NodeSeq): List[(text:String, year:Int)] = {
    val x = (xml \ "hand")
    val list = for (el <- (x \ "card")) yield el
    val hand = for (el <- list) yield cardFromXML(el)
    hand.toList
  }

  def deckFromXML(xml: scala.xml.NodeSeq) ={
    val relevantXML = (xml \ "deck")
    val deck = cardListFromXML(relevantXML)
   deck
  }

  def playerFromXML(xml: scala.xml.NodeSeq):Player = {
    val name = (xml \ "name").text
    val cards = cardListFromXML(xml)
    (name, cards)
  }

  def playerListFromXML(xml: scala.xml.NodeSeq): List[Player] ={
    val relevantXML = (xml \ "players")
    val players = for (player <- (relevantXML \ "player")) yield playerFromXML(player)
    players.toList
  }
*/
}

