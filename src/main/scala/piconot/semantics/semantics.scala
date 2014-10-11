package piconot

import piconot.ir._

package object semantics {
  def eval(ast: AST): String = ast match {
    case PicoString(string) => string
    case PicoModifier(string) => string
    case PicoAbility(string) => if (string == "can") "X" else if (string == "cannot") "x" else "*"
    case PicoDirection(string) => string
    case PicoSurroundings(north, east, west, south) =>
      eval(north) + eval(east) + eval(west) + eval(south)

    case MakeCommand(streetWithModifier, surroundings, go) => eval(streetWithModifier)
    case GetStreet(street: Command, modifier) => eval(street) + eval(modifier)
    case GetSurroundings(ability, direction, surroundings) => eval(ability) + eval(direction) + eval(surroundings)
    case GetAction(direction, streetWithModifier) => eval(direction) + eval(streetWithModifier)
  }
}
