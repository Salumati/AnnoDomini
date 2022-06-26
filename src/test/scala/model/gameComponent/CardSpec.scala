package model.gameComponent

import model.gameComponent.Card
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec {
  val card:Card = Card("A Card Text", 10)
  "A Card" when{
      "new" should  {
        "be of its own kind" in{
          assert(card.isInstanceOf[Card])
        }
        "have a year" which{
          "is an Integeger" in {
            assert(card.year == 10)
          }
          "has a String representation" in {
            assert(card.showYear == "10")
          }
        }
        "Have a text" in{
          assert(card.text.isInstanceOf[String])
          assert(card.text == "A Card Text")
        }
        "Have a Stringreperesentation" in {
          assert(card.toString == "[A Card Text]\n")
        }
        "Have a reveal function" in {
          assert(card.reveal == "[year: 10; A Card Text]")
        }
        "should be considered the same as another card with the same text and year" in{
          val cardClone = Card("A Card Text", 10)
          assert(card == cardClone)
        }
    }
  }
}
