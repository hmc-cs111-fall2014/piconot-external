package piconot.ir

/*
 * "If you are on", space, street, [north option], [east option], [west option], [south option], ",", space, go, space, street, ".", new line
 *
 * p ∈ Program ::= c \n p | c (Not doing now) FIX ME
 * c ∈ Command ::= "If you are on " s o go
 * s ∈ Street ::= string m
 * m ∈ Modifier ::= "Rd." | "St." | "Pkwy." | "Ave." | "Blvd."
 * o ∈ option ::= no eo wo so
 * no ∈ North-option ::= "and you " ability " go uptown" | ""
 * eo ∈ East-option ::= "and you " ability " go outta_town" | ""
 * wo ∈ West-option ::= "and you " ability " go into_town" | ""
 * so ∈ South-option ::= "and you " ability " go downtown" | ""
 * a ∈ ability ::= "can" | "cannot"
 * go ∈ Go ::= "go " d " on " s | "teleport to " s
 * d ∈ Direction ::= "uptown" | "outta_town" | "into_town" | "downtown"
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
