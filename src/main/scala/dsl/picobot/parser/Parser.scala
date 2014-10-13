package dsl.picobot.parser

import scala.util.parsing.combinator._
import dsl.picobot.ir._

object PicoParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)
       
    lazy val filename: PackratParser[String] = """[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)""".r ^^ { _.toString }
    
    lazy val program: PackratParser[Program] =
      ("Proof."~"Recall "~filename~"."~consider ^^ 
          {case "Proof."~"Recall "~mazename~"."~consider => Program(Declaration(mazename), consider)}
          )
              
    lazy val consider: PackratParser[Consider] =
      (opt("Consider"~repsep(rule,",")~".") ^^ {
        case Some("Consider"~rules~".") => Consider(rules)
        case None => Consider(List.empty)
        }
          )
    
    lazy val rule: PackratParser[Rule] =
      (lhs~"="~rhs ^^ {case l~"="~r ⇒ Rule(l, r)}
      // DO proper base case, give good error
          )
          
    lazy val lhs: PackratParser[Lhs] =
      (   state~(surrounding*) ^^ {case s~r => Lhs(s, r)}
          )
          
    lazy val surrounding: PackratParser[Surrounding] =
      (   "+"~dir ^^ {case "+"~d ⇒ Plus(d)}
        | "-"~dir ^^ {case "-"~d ⇒ Minus(d)}
        | "*"~dir ^^ {case "*"~d ⇒ Mult(d)}
        )
      
    lazy val rhs: PackratParser[Rhs] =
      (   state~"+"~dir ^^ {case s~"+"~d ⇒ Rhs(s, d)}
        | state~"-"~dir ^^ {case s~"-"~d ⇒ Rhs(s, d)}
        | state~"*"~dir ^^ {case s~"*"~d ⇒ Rhs(s, d)}
        | state ^^ {case s => Rhs(s, Stay())}
        )
      
    lazy val dir: PackratParser[Dir] =
      (   "n" ^^ {case "n" => N()}
        | "e" ^^ {case "e" => E()}
        | "w" ^^ {case "w" => W()}
        | "s" ^^ {case "s" => S()}
        | "η" ^^ {case "n" => N()}
        | "ε" ^^ {case "e" => E()}
        | "ω" ^^ {case "w" => W()}
        | "ς" ^^ {case "s" => S()}
      )
          
    lazy val state: PackratParser[State] = wholeNumber ^^ {s ⇒ State(s.toInt)} 
}