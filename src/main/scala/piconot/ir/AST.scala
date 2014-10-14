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

case class extProgram(firstState: Expr, otherStates: Expr) extends Expr
case class extState(number: Expr, ruleSet: Expr) extends Expr
case class extRuleSet(firstRule: Expr, otherRules: Expr) extends Expr
case class extRule(walls: Expr, direction:Expr, number:Expr) extends Expr
case class extWalls(w1: Expr, w2: Expr, w3: Expr, w4: Expr) extends Expr
case class extWall(in: String) extends Expr
case class extDirection(in: String) extends Expr
case class extNum(n: Int) extends Expr