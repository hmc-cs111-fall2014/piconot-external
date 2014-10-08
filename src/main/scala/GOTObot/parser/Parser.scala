package GOTObot.parser

import scala.util.parsing.combinator._
import GOTObot.ir._

object GOTOParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(prog, s)

    // program
    lazy val prog: PackratParser[Prog] = (
      rule~"\n"~prog ^^ {case r~"\n"~p ⇒ r then p}
      | rule
    )
        
    // rule
    lazy val rule: PackratParser[Rule] = (
      go~" "~to~" "~number ^^ {case g~" "~t~" "~n ⇒ Rule(g,t,n)} // ->(t)(n)}
    )

    // go
    import GO._
    lazy val go: PackratParser[GO] = (
      "GO" ^^^ o
      | "G0" ^^^ *
      | "Go" ^^^ x
    )
    
    // to
    import TO._
    lazy val to: PackratParser[TO] = (
      "TO" ^^^ F
      | "T0" ^^^ L
      | "To" ^^^ X
    )
  
    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s ⇒ Num(s.toInt)}
    
 }
