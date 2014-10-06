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
    ()

  lazy val northOption: PackratParser[Command] =
    ()
  lazy val eastOption: PackratParser[Command] =
    ()
  lazy val westOption: PackratParser[Command] =
    ()
  lazy val southOption: PackratParser[Command] =
    ()

  lazy val ability: PackratParser[Command] =
    ()

  lazy val go: PackratParser[Command] =
    ()

  lazy val direction: PackratParser[Command] =
    ()
  
}
