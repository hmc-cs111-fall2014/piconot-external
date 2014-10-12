package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
	// tell parser to consider whitespace
	override val skipWhitespace = false
	
    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[PicobotProgram] = 
      (
          state~" "~surr~" -> "~mov~" "~state ^^ 
          {case state1~" "~surr1~" -> "~movedir~" "~state2 => 
  			Rule(state1, surr1, movedir, state2)}
      )
                    
    // state
    lazy val state: Parser[State] = 
     ( wholeNumber ^^ {s ⇒ State(s.toInt)} )
    
    // surroundings
    lazy val surr: Parser[Surroundings] =  
    (
      surrCompNorth~surrCompEast~surrCompWest~surrCompSouth ^^ {case a~b~c~d => Surroundings(a, b, c, d)}
    )
    
    // surrounding components (with proper ordering enforced)
    lazy val surrComp: Parser[Char] = 
      ( free | wildcard )
      
     def free: Parser[Char] = 'x'
     def wildcard: Parser[Char] = '*'
    
    lazy val surrCompNorth: Parser[Char] = ( surrComp | north )
    lazy val surrCompEast: Parser[Char] = ( surrComp | east )
    lazy val surrCompWest: Parser[Char] = ( surrComp | west )
    lazy val surrCompSouth: Parser[Char] = ( surrComp | south )
            
    def north: Parser[Char] = 'N'
    def south: Parser[Char] = 'S'
    def east: Parser[Char] = 'E'
    def west: Parser[Char] = 'W'
       
    // movement directions
    lazy val mov: Parser[MoveDirection] =
    (
      movComp ^^ {a => MoveDirection(a)}
    )
    
    lazy val movComp: Parser[Char] = ( north | south | east | west | halt)
    
    def halt: Parser[Char] = 'X'
}
