package piconot
import piconot.ir._
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import picolib.semantics.MoveDirection
import scala.collection.mutable.MutableList

package object semantics {
  
  var returnList: List[Rule] = List();
  
  def eval(ast: AST, rules:MutableList[Rule], currentState: Int): Unit = ast match {
    case extProgram(first, rest) ⇒ {eval(first,rules, 0); eval(rest,rules, 0)}
    case extState(number, ruleset) => {eval(ruleset, rules, numEval(number))}
    case extRuleSet(first, rest) => {eval(first,rules, 0); eval(rest,rules, 0)}
    case extRule(w,d,n) => {rules += ruleEval(currentState,w,d,n)}
    
    
    /*
    case extWall(expr) => {expr match {
      case "*" => Anything
      case "X" => Blocked
      case "_" => Open
    }}
    
    case extDirection(expr) => {expr match {
      case "N" => North
      case "E" => East
      case "W" => West
      case "S" => South
    }}
    * 
    */
  }
  
  def ruleEval(currentState: Int, walls: AST, direction: AST, newState: AST): Rule = {
    Rule(State(currentState.toString), wallsEval(walls), directionEval(direction), State(numEval(newState).toString))
  }
  def numEval(ast: AST): Int = ast match {
     case extNum(i) ⇒ i
  }
  def wallEval(ast: AST): picolib.semantics.RelativeDescription = ast match {
    case extWall(expr) => {expr match {
      case "*" => Anything
      case "X" => Blocked
      case "_" => Open
    }}
  }
  def wallsEval(ast: AST) : picolib.semantics.Surroundings = ast match {
    case extWalls(n,e,w,s) => {Surroundings(wallEval(n),wallEval(e),wallEval(w),wallEval(s))}
  }
  def directionEval(ast: AST): picolib.semantics.MoveDirection = ast match {
    case extDirection(expr) => {expr match {
      case "N" => North
      case "E" => East
      case "W" => West
      case "S" => South
    }}
  }
}