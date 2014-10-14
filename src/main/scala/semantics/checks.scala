package semantics.checks

import picolib.semantics.Picobot
import picolib.semantics.State
import scala.collection.mutable.MutableList
import picolib.semantics.Blocked
import picolib.semantics.Surroundings
import picolib.semantics.Rule
import picolib.semantics.North
import picolib.semantics.South
import picolib.semantics.RelativeDescription
import picolib.semantics.Anything
import picolib.semantics.East
import picolib.semantics.West
import picolib.semantics.StayHere

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

trait BoxedIn extends ErrorCollector[Picobot] {
  abstract override def check(bot: Picobot): Unit = {
    super.check(bot)
    val blockedIn = bot.rules filter allBlocked
    val newErrors = blockedIn map (r ⇒ new Error(f"Inescapable rule: ${r}"))
    errors ++= newErrors
  }
  
  def allBlocked(rule: Rule) = {
    val surroundings = rule.surroundings
    surroundings.north == Blocked && surroundings.east == Blocked &&
    surroundings.west == Blocked && surroundings.south == Blocked
  }
}

trait MoveToWall extends ErrorCollector[Picobot] {
  abstract override def check(bot: Picobot): Unit = {
    super.check(bot)
    val blockedIn = bot.rules filter movingToBlocked
    val newErrors = blockedIn map (r ⇒ 
        new Error(f"Can't move ${r.moveDirection } toward wall: ${r}"))
    errors ++= newErrors
  }
  
  def movingToBlocked(rule: Rule) = {
    val inDirection: RelativeDescription = rule.moveDirection match {
      case North ⇒ rule.surroundings.north 
      case East ⇒ rule.surroundings.east 
      case West ⇒ rule.surroundings.west 
      case South ⇒ rule.surroundings.south
      case StayHere ⇒ Anything
    } 
    inDirection == Blocked
  }
}