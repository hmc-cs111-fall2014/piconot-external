package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)

    // expressions
    lazy val program: PackratParser[Expr] = 
      (state~program ^^ {case state~program => extProgram(state, program)}
      |state )
      	 
   
    //terms
    lazy val state: PackratParser[Expr] =
      number~"{"~ruleset~"}" ^^ {case num~"{"~rules~"}" => extState(num,rules)}
    // factors
    lazy val ruleset: PackratParser[Expr] =
      ( rule~ruleset ^^ {case rul~rs => extRuleSet(rul,rs)}
       | rule)
      
    lazy val rule: PackratParser[Expr] = 
      walls~"->"~direction~","~number ^^ {case w~"->"~d~","~n => extRule(w,d,n)}
    
    lazy val walls: PackratParser[Expr] = 
     wall~wall~wall~wall ^^ {case w1~w2~w3~w4 => extWalls(w1,w2,w3,w4)}

    lazy val wall: PackratParser[Expr] =
      ("*" ^^ {in => extWall(in)}
      | "_" ^^ {in => extWall(in)}
      | "X" ^^ {in => extWall(in)})
      
     lazy val direction: PackratParser[Expr] =
       ("N" ^^ {in => extDirection(in)}
         | "E" ^^ {in => extDirection(in)} 
         | "W" ^^ {in => extDirection(in)}
         | "S" ^^ {in => extDirection(in)})
    
    // numbers
    def number: Parser[extNum] = wholeNumber ^^ {s ⇒ extNum(s.toInt)}
    
 }
