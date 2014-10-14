package piconot

import piconot.ir._
import java.io.File
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.{East => PLEast}
import picolib.semantics.GUIDisplay
import picolib.semantics.{North => PLNorth}
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.{Rule => PLRule}
import picolib.semantics.{South => PLSouth}
import picolib.semantics.{State => PLState}
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.{West => PLWest}
import scalafx.application.JFXApp
import picolib.semantics.{MoveDirection => PLMoveDirection}
import picolib.semantics.StayHere
import picolib.semantics.RelativeDescription
import scala.collection.mutable.MutableList

package object semantics {
  def eval(program: Program): Picobot = program match {
    case Program(states, mazeName) => runProgram(states, mazeName)
  }
  
  def runProgram(states: List[State], mazeName: String): Picobot = {
    val rules = generateRules(states)
    val maze = Maze("resources" + File.separator + mazeName)
    return new Picobot(maze, rules)
  }
  
  def generateRules(states: List[State]): List[PLRule] = {
    states flatMap generateRulesForState
  }
  
  def generateRulesForState(state: State): List[PLRule] = {
    state.rules map generateRule(state.number)
  }
  
  def generateRule(stateNumber: StateNumber)(rule: Rule) = {
    val surroundings = generateSurroundings(rule.freeDirections, rule.blockedDirections)
    val directionToMove = convertMoveDirection(rule.moveDirection)
    val newState = PLState(rule.newState.number.toString)
    PLRule(PLState(stateNumber.number.toString), surroundings, directionToMove, newState)
  }
  
  def generateSurroundings(freeDirections: List[Surrounding], blockedDirections: List[Surrounding]) = {
    val freeMoveDirections = freeDirections map convertSurrounding
    val blockedMoveDirections = blockedDirections map convertSurrounding
    Surroundings(
      getRelativeDescription(PLNorth, freeMoveDirections, blockedMoveDirections),
      getRelativeDescription(PLEast, freeMoveDirections, blockedMoveDirections),
      getRelativeDescription(PLWest, freeMoveDirections, blockedMoveDirections),
      getRelativeDescription(PLSouth, freeMoveDirections, blockedMoveDirections)
    )
  }
  
  def getRelativeDescription(direction: PLMoveDirection, 
		  					 freeDirectionValues: List[PLMoveDirection],
		  					 blockedDirectionValues: List[PLMoveDirection]): RelativeDescription = {
    if (freeDirectionValues.contains(direction)) {
      Open
    } else if (blockedDirectionValues.contains(direction)) {
      Blocked
    } else {
      Anything
    }
  } 
  
  def convertSurrounding(surrounding: Surrounding): PLMoveDirection = {
    surrounding match {
      case North => PLNorth
      case East => PLEast
      case West => PLWest
      case South => PLSouth
    }
  }

  def convertMoveDirection(moveDirection: MoveDirection): PLMoveDirection = {
    moveDirection match {
      case North => PLNorth
      case East => PLEast
      case West => PLWest
      case South => PLSouth
      case StayPut => StayHere
    }
  }
  
}
