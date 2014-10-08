package GOTObot.ir

/**
 * -----------
 * Grammar
 * -----------
 *
 *       Start symbol p
 *
 *       p ::= r | r "\n" p
 *       r ::= g t n
 *       g ::= "GO" | "Go" | "G0"
 *       t ::= "TO" | "To" | "T0"
 *       n in 0-999
 * 
 */

sealed abstract class AST
sealed abstract class Prog extends AST

object GO extends Enumeration {
  type GO = Value
  val o, x, * = Value // open, blocked, wildcard  
}

object TO extends Enumeration {
  type TO = Value
  val F, L, X = Value // forward, turn left, stay
}
import GO._
import TO._
case class Num(n: Int)
case class Rule(wallCheck: GO, movement: TO, userState: Num) extends Prog
case class MultiRule(rule: Rule, remaining: Prog) extends Prog

