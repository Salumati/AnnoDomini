package util

// copied from Marcos sodoku in Scala
// used for MVC Architecture

trait Observer {
  def update(): Unit
}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers(): Unit = subscribers.foreach(o => o.update())
  // note: this technically has side effects, doesn't it?
  // shouldn't we avoid that?
}
