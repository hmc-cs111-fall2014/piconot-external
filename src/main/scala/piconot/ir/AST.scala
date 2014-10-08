package piconot.ir

/**
 * -----------
 * Grammar
 * -----------
 *  
 *  // states can be any number of digits
 *	digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
 *	state = { digit } ;
 *
 *	// a surrounding is given by the direction or a restriction or a wildcard
 *	direction = "N" | "E" | "W" | "S" ;
 *	surrounding component = direction | "x" | "*" ;
 *	surrounding = surrounding component,  component, surrounding component, surrounding component ;
 *
 *	// left hand side of arrow has state and surrounding 
 *	lhs = state, surrounding ;
 *
 *	// right hand side specifies movement and next state to go to  
 *	movement = direction | "X" ;
 *	rhs = movement, state ;
 *
 *	// a rule is a complete statement, the grammar is any number of rules 
 *	rule = lhs , "->", rhs ; 
 *	grammar = { rule } ; 
 *   
 */

sealed abstract class AST
sealed abstract class PicobotProgram extends AST

case class Rule(state1: State, surr: Surroundings, 
				move: MoveDirection, state2: State) extends PicobotProgram
				
case class State(n: Int) extends PicobotProgram {
  require(n <= 100, "State identifier is too large!")
}
case class Surroundings(char1: Char, char2: Char, 
						char3: Char, char4: Char) extends PicobotProgram

trait SurroundingComponent extends PicobotProgram
case object Blocked extends SurroundingComponent
case object Free extends SurroundingComponent
case object Wildcard extends SurroundingComponent

trait MoveDirection extends PicobotProgram
case object North extends MoveDirection with SurroundingComponent
case object South extends MoveDirection with SurroundingComponent
case object East extends MoveDirection with SurroundingComponent
case object West extends MoveDirection with SurroundingComponent
case object Halt extends MoveDirection

