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
          state~" "~surr ^^ {case state1~" "~surr1 => Rule(state1,surr1)}
      )
                    
    // state
    lazy val state: Parser[State] = 
     ( 
      wholeNumber ^^ {s ⇒ State(s.toInt)}
     )
    
    // surroundings
    lazy val surr: Parser[Surroundings] =  
    (
      surrComp~surrComp~surrComp~surrComp ^^ {case a~b~c~d => Surroundings(a, b, c, d)}
    )
          
    lazy val surrComp: Parser[Char] = 
      (
          free
          | wildcard
          | blocked
      )
      
     def free: Parser[Char] = 'x'
     def wildcard: Parser[Char] = '*'
     def blocked: Parser[Char] = 'N' 
  
    // movement
    lazy val mov: Parser[MoveDirection] =
    (
      ident ^^ {a => MoveDirection(a.charAt(0))}
    )
 }
