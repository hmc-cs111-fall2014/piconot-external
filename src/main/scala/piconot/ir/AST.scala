package piconot.ir

sealed abstract class AST

case class Program(states: List[State], mazeName: String) extends AST
case class State(number: StateNumber, rules: List[Rule]) extends AST
case class Rule(freeDirections: List[CompassDirection],
				blockedDirections: List[CompassDirection],
				moveDirection: MoveDirection,
				newState: StateNumber) extends AST
case class StateNumber(number: Int) extends AST

sealed abstract class MoveDirection extends AST
sealed abstract class CompassDirection extends MoveDirection
case object North extends CompassDirection
case object South extends CompassDirection
case object East extends CompassDirection
case object West extends CompassDirection
case object StayPut extends MoveDirection

				