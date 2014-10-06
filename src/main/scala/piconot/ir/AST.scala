package piconot.ir

sealed abstract class AST

case class Program(states: List[State], mazeName: String) extends AST
case class State(number: StateNumber, rules: List[Rule]) extends AST
case class Rule(freeDirections: List[Surrounding],
				blockedDirections: List[Surrounding],
				moveDirection: MoveDirection,
				newState: StateNumber) extends AST
case class StateNumber(number: Int) extends AST


trait MoveDirection
trait Surrounding
case object North extends Surrounding with MoveDirection
case object South extends Surrounding with MoveDirection
case object East extends Surrounding with MoveDirection
case object West extends Surrounding with MoveDirection
case object StayPut extends MoveDirection
