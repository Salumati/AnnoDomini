package model.gameComponent

case class Card(text: String, year: Int) {
  def showYear: String = year.toString
  def reveal: String = s"[year: $showYear; $text]"

  override def toString: String = s"[$text]\n"
}
