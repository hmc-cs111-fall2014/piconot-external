package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)

    // expressions
    lazy val program: PackratParser[Expr] = 
      (state~program ^^ {case state~program => extProgram(state, program)}
      |state
      |error("Expected a piconot program."))
      	 
    //terms
    lazy val state: PackratParser[Expr] =
      (number~"{"~ruleset~"}" ^^ {case num~"{"~rules~"}" => extState(num,rules)}
      |error("Expected a state."))
    // factors
    lazy val ruleset: PackratParser[Expr] =
      (rule~ruleset ^^ {case rul~rs => extRuleSet(rul,rs)}
      |rule
      |error("Expected a non-empty set of rules."))
      
    lazy val rule: PackratParser[Expr] = 
      (walls~"->"~direction~","~number ^^ {case w~"->"~d~","~n => extRule(w,d,n)}
      |error("Expected a rule."))
    
    lazy val walls: PackratParser[Expr] = 
     (wall~wall~wall~wall ^^ {case w1~w2~w3~w4 => extWalls(w1,w2,w3,w4)}
     |error("Expected a walls definition."))

    lazy val wall: PackratParser[Expr] =
      ("*" ^^ {in => extWall(in)}
      |"_" ^^ {in => extWall(in)}
      |"X" ^^ {in => extWall(in)}
      |error("Expected a wall character"))
      
     lazy val direction: PackratParser[Expr] =
       ("N" ^^ {in => extDirection(in)}
       |"E" ^^ {in => extDirection(in)} 
       |"W" ^^ {in => extDirection(in)}
       |"S" ^^ {in => extDirection(in)}
       |error("Expected a direction."))
    
    // numbers
    def number: Parser[extNum] = 
      (wholeNumber ^^ {s ⇒ extNum(s.toInt)}
      |error("Expected a number"))
    
 }
