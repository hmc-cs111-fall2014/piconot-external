package piconot.ir

/*
 * "If you are on ", street, option, ", ", goTo, streetTo
 *
 * c ∈ Command ::= "If you are on " s o gT sT | c c
 * s ∈ Street ::= string m
 * m ∈ Modifier ::= "Rd." | "St." | "Pkwy." | "Ave." | "Blvd."
 * o ∈ option ::= " and " ability " go " Direction
 * a ∈ ability ::= "can" | "cannot"
 * gT ∈ goTo ::= ", go " d " on "| "teleport to "
 * sT ∈ streetTo ::= s
 * d ∈ Direction ::= "uptown" | "outta town" | "into town" | "downtown"
 *
 */

sealed abstract class AST
sealed abstract class Command extends AST

case class PicoString(string: String) extends Command
case class PicoModifier(string: String) extends Command
case class PicoAbility(string:String) extends Command
case class PicoDirection(string:String) extends Command
case class PicoSurroundings(north:Command, east:Command, west:Command, south:Command) extends Command

case class MakeCommand(streetWithModifier: Command, surroundings: Command, goDirection: Command, goStreet: Command, nextCommand: Command) extends Command
case class GetStreet(street: Command, modifier: Command) extends Command
case class GetSurroundings(ability: Command, direction: Command, surroundings:Command) extends Command
case class GetFinalDirection(direction: Command) extends Command
case class GetFinalStreet(streetWithModifier: Command) extends Command
