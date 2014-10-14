package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[Program] = parseAll(program, s)

    // expressions
    lazy val program: PackratParser[Program] =
      rep1(state)~"RUN MAZE:"~"""[a-zA-Z0-9\._]+""".r ^^ {case states~_~mazeName => Program(states, mazeName)}
    
    lazy val state: PackratParser[State] = 
      "STATE"~stateNumber~":"~rep1(rule) ^^ {case _~stateNum~_~rules => State(stateNum, rules)}
    
    lazy val rule: PackratParser[Rule] =
      ( "FREE DIRECTIONS:"~repsep(surrounding, ",")~
        "BLOCKED DIRECTIONS:"~repsep(surrounding, ",")~
        "MOVE DIRECTION:"~moveDirection~
        "NEW STATE:"~stateNumber ^^ {
        case _~freeDirs~_~blockedDirs~_~moveDir~_~newState => Rule(freeDirs, blockedDirs, moveDir, newState)
      })
    
    lazy val surrounding: PackratParser[Surrounding] = 
      (   "N" ^^ {case _ => North}
        | "E" ^^ {case _ => East}
        | "W" ^^ {case _ => West}
        | "S" ^^ {case _ => South})
    
    lazy val moveDirection: PackratParser[MoveDirection] = 
      (   "N" ^^ {case _ => North}
        | "E" ^^ {case _ => East}
        | "W" ^^ {case _ => West}
        | "S" ^^ {case _ => South}
        | "X" ^^ {case _ => StayPut})
      
    // numbers
    def stateNumber: Parser[StateNumber] = wholeNumber ^^ {s ⇒ StateNumber(s.toInt)}
    
 }
