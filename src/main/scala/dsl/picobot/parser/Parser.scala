package dsl.picobot.parser

import scala.util.parsing.combinator._
import dsl.picobot.ir._

object PicoParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)
    
//    lazy val file: PackratParser[File] = 
//      state
    
//    def state: Parser[State] = wholeNumber ^^ {s ⇒ State(s.toInt)}
    
    lazy val program: PackratParser[Program] =
      ("Proof."~"Recall "~mazename~"."~consider
          )
    
    lazy val consider: PackratParser[Consider] =
      ("Consider"~((rule~",")*)~rule~"." ^^ {
        case "Consider"~((rule~",")*)~rule~"." => Consider(rules++extrarule)
        }
      | "" ^^ {}
          )
    
    lazy val rule: PackratParser[AST] =
      (lhs~"="~rhs ^^ {case l~"="~r ⇒ Rule(l, r)}
      // DO proper base case, give good error
          )
          
    lazy val lhs: PackratParser[Lhs] =
      (   state~(surrounding*) ^^ {case s~r => Lhs(s, r)}
//        | state ^^ {case s => Lhs(s, None)}
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
        // TODO: support greek
      )
//      
//    lazy val rest: PackratParser[Rest] =
//      (   "+"~dir~rest ^^ {case "+"~d~r ⇒ Plus(d, Some(r))}
//        | "-"~dir~rest ^^ {case "-"~d~r ⇒ Minus(d, Some(r))}
//        | "*"~dir~rest ^^ {case "*"~d~r ⇒ Mult(d, Some(r))}
//        | "+"~dir ^^ {case "+"~d ⇒ Plus(d, None)}
//        | "-"~dir ^^ {case "-"~d ⇒ Minus(d, None)}
//        | "*"~dir ^^ {case "*"~d ⇒ Mult(d, None)}
//        )
          
    lazy val state: PackratParser[State] = wholeNumber ^^ {s ⇒ State(s.toInt)} 
          
//    l~"="~r ^^ => state(l), actions(r)
}