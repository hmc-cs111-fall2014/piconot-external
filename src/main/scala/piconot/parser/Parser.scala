package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[Rule] =
      ( lhs~" -> "~rhs ) // TODO: Default case/error handling
          
    // left-hand side
    lazy val lhs: PackratParser[Rule] =
      ( state~" "~surroundings ^^ {case l~" "~r => LHS(l, r)} )
      
    // right-hand side
    lazy val rhs: PackratParser[Rule] =
      ( movement~" "~state )
      
    // state
    lazy val state: PackratParser[Rule] = 
      ()
      
   // surroundings
   lazy val surroundings: PackratParser[Rule] =
     ()
 }
