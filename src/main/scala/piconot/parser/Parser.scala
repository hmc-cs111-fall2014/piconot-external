package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[PicobotProgram] = 
      (
          state~surr ^^  {case state1~surr1 => Rule(state1, surr1)}
          //state~surr~"->"~>state ^^ {case state1~surr1~state2 ⇒ Rule(state1, surr1, state2)}
      )
                    
    // state
    lazy val state: Parser[State] = wholeNumber ^^ {s ⇒ State(s.toInt)}
    
    // surroundings
//   lazy val surr: PackratParser[Surroundings] = 
//     ( abcd
//         )
         
  // lazy val surrComp: Parser[SurroundingComponentType] = letter ^^ {surrComp ⇒ SurroundingComponent(surrComp.toChar)}
     
      lazy val surr: Parser[Surroundings] = ident ^^ {a => Surroundings(a.charAt(0),a.charAt(1), a.charAt(2), a.charAt(3))}
    /*lazy val mov: PackratParser[PicobotProgram] = 
      
      
      
   // surrounding components
   lazy val surrComp: PackratParser[PicobotProgram] = 
     ( Blocked | Free | Wildcard | North | South | East | West)
    
     
   def blocked: Parser[Blocked] = wholeNumber ^^ {s => }*/
 }
