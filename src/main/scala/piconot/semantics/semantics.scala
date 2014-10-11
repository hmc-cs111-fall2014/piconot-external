package piconot

import picolib.semantics._
import piconot.ir._

package object semantics {
  var mapState: Map[String, String] = Map()
  var listRules: List[Rule] = List()
  def eval(ast: AST): Rule = ast match {
    case MakeCommand(streetWithModifier, surroundings, goDirection, goState) =>
      val rule:Rule = Rule(State(extractString(streetWithModifier)),
          getSurroundings(extractSurroundings(surroundings)),
          getDirection(extractString(goDirection)),
          State(extractString(goState)))
      listRules = listRules :+ rule
      rule
  }

  def extractString(ast: AST): String = ast match {
    case PicoString(string) => string
    case PicoModifier(string) => string
    case PicoAbility(string) => string
    case PicoDirection(string) => string
    case PicoSurroundings(north, east, west, south) =>
      extractString(north) + extractString(east) + extractString(west) + extractString(south)
    case GetStreet(street: Command, modifier) => getState(extractString(street) + extractString(modifier))
    case GetFinalDirection(direction) => extractString(direction)
    case GetFinalStreet(streetWithModifier) => extractString(streetWithModifier)
  }

  def extractSurroundings(ast:AST): PicoSurroundings = ast match {
    case PicoSurroundings(north, east, west, south) => PicoSurroundings(north, east, west, south)
    case GetSurroundings(ability, direction, surroundings) =>
      var newSurroundings = extractSurroundings(surroundings)
      if (direction == PicoDirection("uptown"))
        PicoSurroundings(ability,
          newSurroundings.east,
          newSurroundings.west,
          newSurroundings.south)
      else if (direction == PicoDirection("outta_town"))
        PicoSurroundings(newSurroundings.north,
          ability,
          newSurroundings.west,
          newSurroundings.south)
      else if (direction == PicoDirection("into_town"))
        PicoSurroundings(newSurroundings.north,
          newSurroundings.east,
          ability,
          newSurroundings.south)
      else
        PicoSurroundings(newSurroundings.north,
          newSurroundings.east,
          newSurroundings.west,
          ability)
  }

  def getState(street: String): String = {
    mapState.get(street) match {
      case Some(stateNum: String) => stateNum
      case None => {
        mapState = mapState + (street -> mapState.size.toString)
        getState(street)
      }
    }
  }

  def getRelDes(action: String): RelativeDescription = {
    action match {
      case "can" => Open // can
      case "cannot" => Blocked // can't
      case "null" => Anything // default
    }
  }

  def getSurroundings(surroundings: PicoSurroundings): Surroundings = {
    Surroundings(getRelDes(extractString(surroundings.north)),
      getRelDes(extractString(surroundings.east)),
      getRelDes(extractString(surroundings.west)),
      getRelDes(extractString(surroundings.south)))
  }

  def getDirection(direction: String): MoveDirection = {
    direction match {
      case "uptown" => North
      case "outta_town" => East
      case "into_town" => West
      case "downtown" => South
      case _ => StayHere
    }
  }
}