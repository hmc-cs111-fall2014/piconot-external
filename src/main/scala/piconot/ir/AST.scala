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

// abstract classes for AST and full program
sealed abstract class AST
sealed abstract class PicobotProgram extends AST

// top level program pieces
case class Program(filename: String, rules: Rules) extends PicobotProgram
case class Rules(rulesList: List[Rule]) extends PicobotProgram
case class Rule(state1: State,  surr: Surroundings, mov: MoveDirection, state2: State) extends PicobotProgram

// state class with error checking
case class State(n: Int) extends PicobotProgram {
  require(n < 100, "State identifier is too large!")
  require(n >= 0, "State identifier is too small!")
}

// surroundings
case class Surroundings(north: SurroundingComponentType, east: SurroundingComponentType, 
						west: SurroundingComponentType, south: SurroundingComponentType) extends PicobotProgram

// class, trait, and objects for surrounding components
case class SurroundingComponent(c: Char)			
trait SurroundingComponentType extends PicobotProgram
case object Blocked extends SurroundingComponentType
case object Free extends SurroundingComponentType
case object Wildcard extends SurroundingComponentType

// class, trait, and objects for movement directions
case class MoveDirection(dir: MoveDirectionType) extends PicobotProgram
trait MoveDirectionType extends PicobotProgram
case object MoveNorth extends MoveDirectionType
case object MoveSouth extends MoveDirectionType
case object MoveEast extends MoveDirectionType
case object MoveWest extends MoveDirectionType
case object Halt extends MoveDirectionType

