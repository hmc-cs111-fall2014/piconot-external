package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[PicobotProgram] = 
      (
          state~surr~"->"~mov~state ^^  {case state1~surr1~"->"~movedir~state2 => Rule(state1, surr1, movedir, state2)}
      )
                    
    // state
    lazy val state: Parser[State] = 
     ( 
      wholeNumber ^^ {s ⇒ State(s.toInt)}
     )
    
    // surroundings
    lazy val surr: Parser[Surroundings] =  
    (
      ident ^^ {a => Surroundings(a.charAt(0),a.charAt(1), a.charAt(2), a.charAt(3))}
    )
    
    // movement
    lazy val mov: Parser[MoveDirection] =
    (
      ident ^^ {a => MoveDirection(a.charAt(0))}
    )
}
