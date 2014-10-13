package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)

    // expressions
    lazy val program: PackratParser[Expr] = 
      (state~program ^^ {case state~program => Program(state, program)}
      |state )
      	 
   
    //terms
    lazy val state: PackratParser[Expr] =
      number~"{"~ruleset~"}" ^^ {case num~"{"~rules~"}" => State(num,rules)}
    // factors
    lazy val ruleset: PackratParser[Expr] =
      ( rule~ruleset ^^ {case rul~rs => RuleSet(rul,rs)}
       | rule)
      
    lazy val rule: PackratParser[Expr] = 
      walls~"->"~direction~","~number ^^ {case w~"->"~d~","~n => Rule(w,d,n)}
    
    lazy val walls: PackratParser[Expr] = 
     wall~wall~wall~wall ^^ {case w1~w2~w3~w4 => Walls(w1,w2,w3,w4)}

    lazy val wall: PackratParser[Expr] =
      ("*" ^^ {in => Wall(in)}
      | "_" ^^ {in => Wall(in)}
      | "X" ^^ {in => Wall(in)})
      
     lazy val direction: PackratParser[Expr] =
       ("N" ^^ {in => Direction(in)}
         | "E" ^^ {in => Direction(in)} 
         | "W" ^^ {in => Direction(in)}
         | "S" ^^ {in => Direction(in)})
    
    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s ⇒ Num(s.toInt)}
    
 }
