package pico_ought.parser

import picolib.semantics._

import scala.util.parsing.combinator._
import pico_ought.ir._

object PicoOughtParser extends JavaTokenParsers with PackratParsers with RegexParsers {

    // Ignores comments of the form: # <comment> #
    override val whiteSpace = """([\s\n\t]|#[^#]*#)+""".r

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(program, s)

    lazy val program: PackratParser[AST] =
    (     (section*) ^^ {case s => Program(s.toList)}
      ||| non_if_command // Just for testing purposes, we allow parsing of a single command
       | failure("Not valid program.") 
        )

    lazy val section: PackratParser[Section] = 
    (  label~":"~(command*) ^^ {case l~":"~commands => Section(l, commands.flatten) }
        )

    lazy val command: PackratParser[List[Command]] =
    (   non_if_command ^^ {case a => List(a)}
      | if_block~non_if_command ^^ {case l~r => List(l,r)}
        )

    lazy val non_if_command: PackratParser[Command] =
    (   face<~"."
      | turn<~"."
      | go<~"."
      | do_command<~"."
        )

    lazy val if_block: PackratParser[Command] =
    (   if_word~>condition<~(","~"then") ^^ { case cond => If(cond) }
      )

    lazy val face: PackratParser[Command] = 
    (   face_word~"up"    ^^^ { Face(UP) }
      | face_word~"right" ^^^ { Face(RIGHT) }
      | face_word~"down"  ^^^ { Face(DOWN) }
      | face_word~"left"  ^^^ { Face(LEFT) }
        )



    lazy val turn: PackratParser[Command] = 
    (   turn_word~"left"   ^^^ { Turn(L) }
      | turn_word~"right"  ^^^ { Turn(R) }
      | turn_word~"around" ^^^ { Turn(AROUND) }
        )


    lazy val go: PackratParser[Command] = 
    (   go_word~>go_dir<~"once" ^^ {
                    case "forwards"  => Go(FORWARDS,   None)
                    case "right"     => Go(RIGHTWARDS, None)
                    case "backwards" => Go(BACKWARDS,  None)
                    case "left"      => Go(LEFTWARDS,  None) }
      | go_word~>go_dir~"while"~condition ^^ {
                    case "forwards"~"while"~cond  => Go(FORWARDS,   Some(cond))
                    case "right"~"while"~cond     => Go(RIGHTWARDS, Some(cond))
                    case "backwards"~"while"~cond => Go(BACKWARDS,  Some(cond))
                    case "left"~"while"~cond      => Go(LEFTWARDS,  Some(cond)) }
      | go_word~"all"~"the"~"way"~>go_dir ^^ {
                    case "forwards"  => Go(FORWARDS,   Some(Map(FORWARDS -> Open)))
                    case "right"     => Go(RIGHTWARDS, Some(Map(RIGHTWARDS -> Open)))
                    case "backwards" => Go(BACKWARDS,  Some(Map(BACKWARDS -> Open)))
                    case "left"      => Go(LEFTWARDS,  Some(Map(LEFTWARDS -> Open))) }
        )
    
    lazy val condition: PackratParser[Map[Int, RelativeDescription]] = 
    (     condition_part 
      ||| condition_part~"and"~condition ^^ { case l~"and"~r => l ++ r }
        )

    lazy val condition_part: PackratParser[Map[Int, RelativeDescription]] = 
    (   "wall"~>cond_dir ^^ { case dir => Map(dir -> Blocked) }
      | "open"~>cond_dir ^^ { case dir => Map(dir -> Open) }
        )

    lazy val cond_dir: PackratParser[Int] =
    (   "in"~"front" ^^^ { FORWARDS }
      | "on"~"right" ^^^ { RIGHTWARDS }
      | "behind"     ^^^ { BACKWARDS }
      | "on"~"left"  ^^^ { LEFTWARDS }
        )


    lazy val do_command: PackratParser[Command] = 
    (   do_word~>label ^^ { case label => Do(label) }
        )


    def face_word: Parser[String] = ("Face" | "face")
    def turn_word: Parser[String] = ("Turn" | "turn")
    def go_word: Parser[String] = ("Go" | "go")
    def go_dir: Parser[String] = ("forwards" | "right" | "backwards" | "left") 
    def do_word: Parser[String] = ("Do" | "do")
    def if_word: Parser[String] = ("If" | "if")
    def label: Parser[String] = """[^#\.:\n]+""".r
}