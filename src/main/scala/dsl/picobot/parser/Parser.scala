package dsl.picobot.parser

import scala.util.parsing.combinator._
import dsl.picobot.ir._

object PicoParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(file, s)
    
    lazy val file: PackratParser[File] = 
      state
    
    def state: Parser[State] = wholeNumber ^^ {s ⇒ State(s.toInt)}
}