package semantics.checks

import picolib.semantics.Picobot
import picolib.semantics.State
import scala.collection.mutable.MutableList

class ErrorCollector[T] {
  val errors: MutableList[Error] = MutableList.empty[Error]
  
  def check(value: T): Unit = {}
}

trait UndefinedStates extends ErrorCollector[Picobot] {
  abstract override def check(bot: Picobot): Unit = {
    super.check(bot)
    val startStates = bot.rules map (_.startState)
    val endStates = bot.rules map (_.endState)
    val undefined = endStates filterNot (startStates contains _)
    val newErrors = undefined map (s ⇒ new Error(f"Undefined state: ${s}"))
    errors ++= newErrors
  }
}

trait UreachableStates extends ErrorCollector[Picobot] {
  abstract override def check(bot: Picobot): Unit = {
    super.check(bot)
    val startStates = (bot.rules map (_.startState)).tail // the first state is always reachable
    val endStates = bot.rules map (_.endState)
    val unreachable = startStates filterNot (endStates contains _)
    val newErrors = unreachable map (s ⇒ new Error(f"Unreachable state: ${s}"))
    errors ++= newErrors
  }
}