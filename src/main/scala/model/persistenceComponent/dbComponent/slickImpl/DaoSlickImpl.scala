package model.persistenceComponent.dbComponent.slickImpl

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import slick.dbio.{DBIO, DBIOAction}
import slick.jdbc.MySQLProfile.api._

import com.typesafe.config._

import model.persistenceComponent.dbComponent.DaoInterface
import model.persistenceComponent.XMLImpl.FileIOAsXML
import model.gameComponent.{Card, Table, Deck, Player, TableGenerator}


class DaoSlickImpl() extends DaoInterface{
  
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
  val cardsTable = TableQuery[CardTable]
  val gamesTable = TableQuery[GameTable]
  val playersTable = TableQuery[PlayerTable]

  val gameID = "game1"
  val deckID = "deck1"
  val playerID = gameID+"P1"

  override def save(table: Table): Unit = {
    println("saving game in db")
    val db = Database.forConfig("mysqldb")
    try{
      val createTablesIfNotExist = db.run(DBIO.seq((gamesTable.schema ++ cardsTable.schema ++ playersTable.schema).createIfNotExists))
      
      createTablesIfNotExist.onComplete{
        case Success(s) => println("successfully created Databases")
        case Failure(f) => println("Failed to create Database: " + f)
      }
      val allGametableCards = table.cardsOnTable
      val allPlayers = table.players
      val allDeckCards = table.deck.cards

      Await.ready(createTablesIfNotExist, 3 minute)

      val insertGameCards = db.run(cardsTable ++= ((table.cardsOnTable).map(c => (1, c.year, c.text, gameID))))
      val insertPlayers = db.run(playersTable ++= table.players.map(p => ((gameID + p.name), p.name, gameID)))
      val insertPCards = db.run(cardsTable ++= table.players.flatMap(p => p.hand.map(c => (1, c.year, c.text, (gameID+"_"+p.name)))))
      val insertGame = db.run(gamesTable += (gameID, deckID))
      val insertDeckCards = db.run(cardsTable ++= (table.deck.cards).map(c => (1, c.year, c.text, deckID)))

      insertDeckCards.onComplete{
        case Success(value) => println("successfully saved deck cards!")
        case Failure(exception) => println("failed to insert deck cards: " + exception)
      }
      insertGame.onComplete{
        case Success(value) => println("successfully saved game!")
        case Failure(exception) => println("failed to insert game: " + exception)
      }
      insertGameCards.onComplete{
        case Success(value) => println("successfully saved game cards!")
        case Failure(exception) => println("failed to insert game cards: " + exception)
      }
      insertPlayers.onComplete{
        case Success(value) => println("successfully saved players!")
        case Failure(exception) => println("failed to insert players: " + exception)
      }
      insertPlayers.onComplete{
        case Success(value) => println("successfully saved players cards!")
        case Failure(exception) => println("failed to insert players cards: " + exception)
      }

      Await.ready(insertGame, 60 seconds)
      Await.ready(insertGameCards, 60 seconds)
      Await.ready(insertDeckCards, 60 seconds)
      Await.ready(insertPlayers, 60 seconds)
      Await.ready(insertPCards, 60 seconds)
    } finally db.close

  }

  override def load(): Table = {
    println("loading game form db")
    val db = Database.forConfig("mysqldb")
    try{
      val queryGetAllGameCards = cardsTable.filter(_.card_owner === gameID)
      val getAllGameCards = db.run(queryGetAllGameCards.result)
      val getAllPlayers = db.run(playersTable.filter(_.game_id === gameID).result)
      val getDeck = db.run(cardsTable.filter(_.card_owner === deckID).result)

      getAllGameCards.onComplete{
        case Success(value) => println("loaded game cards")
        case Failure(exception) => println("failed to load game cards:" + exception)
      }
      getAllPlayers.onComplete{
        case Success(value) => println("loaded players")
        case Failure(exception) => println("failed to load players:" + exception)
      }
      getDeck.onComplete{
        case Success(value) => println("loaded deck")
        case Failure(exception) => println("failed to load deck:" + exception)
      }
      
      val allPlayers = Await.result(getAllPlayers, 120 second)
      val allPlayersWithCards = {
        for (player <- allPlayers) yield {
          val playerName = player._2
          val getPlayerCards = Await.result(db.run(cardsTable.filter(_.card_owner === player._1).result), 180 seconds)
          val playerCards = for(card <- getPlayerCards) yield Card(card._3, card._2)
          Player(playerName, playerCards.toList)
        }
      }

      val allGameCards = Await.result(getAllGameCards, 120 second)
      val deck = Await.result(getDeck, 120 second)

      val deckCards = for(card <- deck) yield Card(card._3, card._2)
      val gameCards = for(card <- allGameCards) yield Card(card._3, card._2)

      Table(allPlayersWithCards.toList, gameCards.toList, Deck(deckCards.toList))

    } finally db.close
  }

  def deleteAllEntries(): Unit = {
    println("deleteng all db entires")
    val db = Database.forConfig("mysqldb")
    try {
      val deleteCards = db.run(cardsTable.delete)
      val deletePlayers = db.run(playersTable.delete)
      val deleteGames = db.run(gamesTable.delete)

    } finally db.close()
  }
  
  def checkConfig(): Unit = {
    val conf = ConfigFactory.load()
    println("driver: " + conf.getString("mysqldb.driver"))
    println("user: " + conf.getString("mysqldb.user"))
    println("password: " + conf.getString("mysqldb.password"))
  }
}

