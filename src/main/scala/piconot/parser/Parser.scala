package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
	// tell parser to consider whitespace
	override val skipWhitespace = false
	
    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    
    // rules
    
    lazy val program: Parser[PicobotProgram] = 
      (
          repsep(rule, "\n") ^^ {case list1 => Rules(list1)}
      )
    lazy val rule: PackratParser[Rule] = 
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
    lazy val surrComp: Parser[SurroundingComponentType] = 
      ( free | wildcard )
      
     def free: Parser[SurroundingComponentType] = 'x' ^^^ Free
     def wildcard: Parser[SurroundingComponentType] = '*' ^^^ Wildcard
    
    lazy val surrCompNorth: Parser[SurroundingComponentType] = ( surrComp | north )
    lazy val surrCompEast: Parser[SurroundingComponentType] = ( surrComp | east )
    lazy val surrCompWest: Parser[SurroundingComponentType] = ( surrComp | west )
    lazy val surrCompSouth: Parser[SurroundingComponentType] = ( surrComp | south )
    
    lazy val surrComps = ( north | south | east | west )
    def north: Parser[SurroundingComponentType] = 'N' ^^^ Blocked
    def south: Parser[SurroundingComponentType] = 'S' ^^^ Blocked
    def east: Parser[SurroundingComponentType] = 'E' ^^^ Blocked
    def west: Parser[SurroundingComponentType] = 'W' ^^^ Blocked
       
    // movement directions
    lazy val mov: Parser[MoveDirection] =
    (
      movComp ^^ {a => MoveDirection(a)}
    )
    
    lazy val moveDirs = ( movNor | movSouth | movEast | movWest )
    def movNor: Parser[MoveDirectionType] = 'N' ^^^ MoveNorth
    def movSouth: Parser[MoveDirectionType] = 'S' ^^^ MoveSouth
    def movEast: Parser[MoveDirectionType] = 'E' ^^^ MoveEast
    def movWest: Parser[MoveDirectionType] = 'W' ^^^ MoveWest
    
    lazy val movComp: Parser[MoveDirectionType] = ( moveDirs | halt)
    
    def halt: Parser[MoveDirectionType] = 'X' ^^^ Halt
}
