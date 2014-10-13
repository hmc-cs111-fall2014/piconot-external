package piconotexternal

import java.io.File
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.MoveDirection
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.RelativeDescription
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.StayHere
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import scalafx.application.JFXApp

import piconotexternal.ir._

package object semantics {

  var globalRules:List[Rule] = List();
  var numberOfStates = 0
  var stringToStateMap: Map[String, State] = Map[String, State]()

  // FIXME placeholder NUKE THIS LATER
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  // FIXME this might want to return something else
  //  for example Int -> error code?
  def eval(ast: AST):Int = ast match {
    case RulesList(rules) => {
      rules.foreach(x => evalRule(x))
      object Bot extends Picobot(emptyMaze, globalRules) with TextDisplay with GUIDisplay
      stage = Bot.mainStage;
      return 1;
    }

    case _ => throw new IllegalStateException("Malformed output from parser")
  }

  def evalRule(ast: AST)= ast match {
    case InState(oldState, caselist) => {caselist.foreach(x =>
      makeRule(oldState, x))}
    case _ => throw new IllegalStateException("Malformed output from parser")
  }

  //var currentState:State = new State("0");

  def nextStateNum():String = {
    numberOfStates += 1
    return numberOfStates.toString()
  }

  def makeRule(oldState:MyState, single:Single): Int = {
    val start:State = makeState(oldState.stringState);
    val end:State = makeState(single.newState.state.stringState)

    val surr:Surroundings = parseSurroundings(single.news.news.stringDir)
    val dir:MoveDirection = parseMove(single.newDir.newDir.charDir)

    globalRules = globalRules ::: List(Rule(start, surr, dir, end));


    return 1; // Success!
  }

  // Parse the surroundings
  def parseSurroundings(surroundings: String): Surroundings = {

    // Helper to map from chars to semantics.RelativeDescription
    def charToSurr(x:Char):RelativeDescription = {
      x match {
        case close:Char if "NEWS".contains(close) => Blocked
        case 'x' => Open
        case '*' => Anything
      }
    }

    val tmp = surroundings.toList.map(y => charToSurr(y))
    val semanticSurroundings = Surroundings(tmp(0), tmp(1), tmp(2), tmp(3))
    return semanticSurroundings;
  }

  // Creates a new state from state name and rules
  def makeState(stateName: String):State = {
    if (stringToStateMap.contains(stateName)) {
      return stringToStateMap(stateName)
    } else {
      val newState = State(nextStateNum)
      stringToStateMap += stateName -> newState
      return newState;
    }
  }



  // parse the destination state and direction
  def parseMove(direction: Char): MoveDirection = {

    // Helper to map from input move direction to semantics.MoveDirection
    def strToMoveDir(x:Char):MoveDirection = {
      x match {
        case 'N' => North
        case 'E' => East
        case 'W' => West
        case 'S' => South
        case 'X' => StayHere
      }
    }

    return strToMoveDir(direction);
  }

}
