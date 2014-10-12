package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(rule, s)

    // rules
    lazy val rule: PackratParser[PicobotProgram] = 
      (
          state~surr ^^ {case state1~surr1 => Rule(state1,surr1)}
          //state~surr~"->"~mov~state ^^  
          //{case state1~surr1~"->"~movedir~state2 => Rule(state1, surr1, movedir, state2)}
      )
                    
    // state
    lazy val state: Parser[State] = 
     ( 
      wholeNumber ^^ {s ⇒ State(s.toInt)}
     )
    
    // surroundings
    lazy val surr: Parser[Surroundings] =  
    (
      //pattern~pattern~pattern~pattern ^^ {case a~b~c~d => Surroundings(a, b, c, d)}
      pattern2  ^^ {case a => Surroundings(a.charAt(0),a.charAt(1), a.charAt(2), a.charAt(3))}
       
      //starOrChar ^^ {a => Surroundings(a.charAt(0),a.charAt(1), a.charAt(2), a.charAt(3))}
    )
    
    lazy val pattern2: Parser[String] = 
      (
          pattern~pattern~pattern~pattern ^^ {case a~b~c~d => a.toString + b.toString + c.toString + d.toString}
      )
          
    lazy val pattern: Parser[Char] = 
      (
          free
          | wildcard
          | blocked
      )
      
      
      
//     lazy val pattern2: Parser[String] = 
//       (
//         pattern~pattern~pattern~pattern ^^ 
//         )
      
     def free: Parser[Char] = 'x'
     def wildcard: Parser[Char] = '*'
     def blocked: Parser[Char] = 'N' 
       
//     lazy val blocked: Parser[Char] =
//       ( north
//         | South
//         | West
//         | East
//           )
//      
//     def north: Parser[North] = 
//        
       
  
    // movement
    lazy val mov: Parser[MoveDirection] =
    (
      ident ^^ {a => MoveDirection(a.charAt(0))}
    )
 }
