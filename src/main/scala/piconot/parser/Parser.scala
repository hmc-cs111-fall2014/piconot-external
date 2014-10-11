package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[PicobotProgram] = 
      (
          state ^^ {case s ⇒ Rule(s)}
      )
                    
    // state
    lazy val state: Parser[State] = wholeNumber ^^ {s => State(s.toInt)}
    
    // surroundings
    /*lazy val surr: PackratParser[PicobotProgram] = 
      
    lazy val mov: PackratParser[PicobotProgram] = 
      
      
      
   // surrounding components
   lazy val surrComp: PackratParser[PicobotProgram] = 
     ( Blocked | Free | Wildcard | North | South | East | West)
    
     
   def blocked: Parser[Blocked] = wholeNumber ^^ {s => }*/
 }
