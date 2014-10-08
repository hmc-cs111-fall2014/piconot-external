package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

  // need to specify what whitespace is important (like new lines)
  // have main AST node that takes in program and holds total state

  def apply(s: String): ParseResult[AST] = parseAll(command, s)

  lazy val command: PackratParser[Command] =
    "If you are on"~street~option~go ^^ {case "If you are on"~s~o~g => MakeCommand(s,o,g)}

  lazy val street: PackratParser[Command] =
    string~modifier ^^ {case s~m => GetStreet(s,m)}

  lazy val string: PackratParser[Command] =
     "a" ^^ {case _ => PicoString("a")}

  lazy val modifier: PackratParser[Command] =
    (   "Rd." ^^ {case s => PicoModifier(s)}
      | "St." ^^ {case s => PicoModifier(s)}
      | "Pkwy." ^^ {case s => PicoModifier(s)}
      | "Ave." ^^ {case s => PicoModifier(s)}
      | "Blvd." ^^ {case s => PicoModifier(s)}
    )

  lazy val option: PackratParser[Command] =
    ("and you"~ability~"go"~direction ^^ {case "and you"~a~"go"~d => GetSurroundings(a, d,
      PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))}
      | "and you"~ability~"go"~direction~option ^^ {case "and you"~a~"go"~d~o => GetSurroundings(a, d, o)})

  lazy val ability: PackratParser[Command] =
    ("can" ^^ {case s => PicoAbility(s)}
      | "cannot" ^^ {case s => PicoAbility(s)})

  lazy val go: PackratParser[Command] =
    ("go"~direction~"on"~street ^^ {case "go"~d~"on"~s => GetAction(d, s)}
      | "teleport to"~street ^^ {case "teleport to"~s => GetAction(PicoDirection("stay"), s)})

  lazy val direction: PackratParser[Command] =
    ("uptown" ^^ {case s => PicoDirection(s)}
      | "outta_town" ^^ {case s => PicoDirection(s)}
      | "into_town" ^^ {case s => PicoDirection(s)}
      | "downtown" ^^ {case s => PicoDirection(s)})
  
}
