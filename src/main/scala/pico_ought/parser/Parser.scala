package pico_ought.parser

import scala.util.parsing.combinator._
import pico_ought.ir._

object PicoOughtParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(face, s)

    lazy val face: PackratParser[Command] = 
    (   face_word~>"up"~"." ^^^ { Face(UP) }
      | face_word~>"right"~"." ^^^ { Face(RIGHT) }
      | face_word~>"down"~"." ^^^ { Face(DOWN) }
      | face_word~>"left"~"." ^^^ { Face(LEFT) }
        )

    lazy val face_word: PackratParser[String] = ("Face" | "face")
}