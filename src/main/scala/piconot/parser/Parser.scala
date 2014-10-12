package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(command, s)

  // Matches either a command by itself or a command followed by any number of additional commands
  // Tracks additional commands in the last argument to MakeCommand
  lazy val command: PackratParser[Command] =
    ("If you are on"~street~option~goDirection~goState~command ^^ {case "If you are on"~s~o~gd~gs~c => MakeCommand(s,o,gd,gs,c)}
    |"If you are on"~street~option~goDirection~goState ^^ {case "If you are on"~s~o~gd~gs => MakeCommand(s,o,gd,gs, PicoString("null"))})

  // Matches a string of letters and a modifier
  lazy val street: PackratParser[Command] =
    string~modifier ^^ {case s~m => GetStreet(s,m)}

  // Uses regex to match to any set of letters
  lazy val string: PackratParser[Command] =
    """[a-zA-Z_]\w*""".r ^^ {case x => PicoString(x)}

  // Matches a set of predefined modifiers
  lazy val modifier: PackratParser[Command] =
    (   "Rd." ^^ {case s => PicoModifier(s)}
      | "St." ^^ {case s => PicoModifier(s)}
      | "Pkwy." ^^ {case s => PicoModifier(s)}
      | "Ave." ^^ {case s => PicoModifier(s)}
      | "Blvd." ^^ {case s => PicoModifier(s)}
    )

  // Matches either an option by itself or a command followed by any number of additional options
  // Tracks additional options in the last argument to GetSurroundings
  lazy val option: PackratParser[Command] =
    ("and"~ability~"go"~direction~option ^^ {case "and"~a~"go"~d~o => GetSurroundings(a, d, o)}
     | "and"~ability~"go"~direction ^^ {case "and"~a~"go"~d => GetSurroundings(a, d,
      PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))})

  // Placed in the given order so it matches cannot first
  lazy val ability: PackratParser[Command] =
    ("cannot" ^^ {case s => PicoAbility(s)}
      |"can" ^^ {case s => PicoAbility(s)})

  // Matches either a new direction to turn or staying at the same direction
  lazy val goDirection: PackratParser[Command] =
    (", go"~direction~"on" ^^ {case ", go"~d~"on" => GetFinalDirection(d)}
      | ", teleport to" ^^ {case ", teleport to" => GetFinalDirection(PicoDirection("stay"))})

  // Matches the final street
  lazy val goState: PackratParser[Command] =
    street ^^ {case s => GetFinalStreet(s)}

  // Matches a set of predefined directions
  lazy val direction: PackratParser[Command] =
    ("uptown" ^^ {case s => PicoDirection(s)}
      | "outta town" ^^ {case s => PicoDirection(s)}
      | "into town" ^^ {case s => PicoDirection(s)}
      | "downtown" ^^ {case s => PicoDirection(s)})
  
}
