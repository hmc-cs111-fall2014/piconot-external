package piconot.ir

/*
 * "If you are on", space, street, [north option], [east option], [west option], [south option], ",", space, go, space, street, ".", new line
 *
 * p ∈ Program ::= c \n p | c
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

class AST {
  sealed abstract class AST
  sealed abstract class Command extends AST

  case class GetStreet(street: String, modifier: String) extends Command
  case class GetSurroundings(ability: String, direction: String) extends Command
  case class GetAction(direction: String, streetWithModifier: String) extends Command

}
