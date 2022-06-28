import model.persistenceComponent.dbComponent.slickImpl._

import scala.io.StdIn.readLine


import model.gameComponent.TableGenerator
import model.gameComponent.Table

object DBTest {
    
    def main(args:Array[String]):Unit = {

    println("Welcome to DB Test!")
    val dbtest = new DaoSlickImpl()

    /*
    println("show config:")
    dbtest.checkConfig()
        */
    println("check if saving works:")
    val tg = new TableGenerator()
    val t = tg.createTable(2, 60)
    println("the saved table: " + t)
    dbtest.save(t)

    println("check if loading works: ")
    val t2 = dbtest.load
    println("the loaded table: " + t2)

    println("delete tables (y/n)?")
    val input = readLine()
    if(input == "y") dbtest.deleteAllEntries()

    println("goodbye!")
    }
    
}
