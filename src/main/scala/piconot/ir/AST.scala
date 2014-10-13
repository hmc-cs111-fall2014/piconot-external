package piconot.ir

/**
 * -----------
 * Grammar
 * -----------
 * 
 *                   program
 * 		
 *       p ∈ program ::= state | state + program
 *       s ∈ State ::= ruleset
 *       rs ∈ Ruleser := rule | rule + ruleset
 *       r ∈ Rule ::= walls + "->" + direction + "," + number
 *       w ∈ Walls ::= string
 *       direction ::= string
 *  
 */

sealed abstract class AST
sealed abstract class Expr extends AST

case class Program(firstState: Expr, otherStates: Expr) extends Expr
case class State(number: Expr, ruleSet: Expr) extends Expr
case class RuleSet(firstRule: Expr, otherRules: Expr) extends Expr
case class Rule(walls: Expr, direction:Expr, number:Expr) extends Expr
case class Walls(w1: Expr, w2: Expr, w3: Expr, w4: Expr) extends Expr
case class Wall(in: String) extends Expr
case class Direction(in: String) extends Expr
case class Num(n: Int) extends Expr