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
sealed abstract class Rule extends AST

case class State(n: Int) extends Rule
case class Direction(c: Char) extends Rule
case class SurroundingComponent(c: Char) extends Rule
case class Surrounding(north: SurroundingComponent, 
					   east: SurroundingComponent,
					   west: SurroundingComponent, 
					   south: SurroundingComponent) extends Rule
case class LHS(state: State, surrounding: Surrounding) extends Rule
case class RHS(movement: Char, state: State) extends Rule
