package priorityBot.semantics

import priorityBot.ir._
import picolib.maze.Maze
import scalafx.application.JFXApp
import java.io.File
import piconot._

package object semantics {
  def eval(ast: AST): Unit = ast match {
    case Picobot(mazeName, rules: AST) => 
      object Piconot extends JFXApp {
        val maze = Maze("resources" + File.separator + mazeName)
        
        val rules = makeRules( eval(rules) )
      }
    case Rules(ruleList) =>
      // TAKE CARE OF *, keep track of previously used directions
      ruleList map (eval)
    case Rule(cardinal, d1, d2, d3, d4) =>
      eval(cardinal) ->(eval(d1), eval(d2), eval(d3), eval(d4))  
    case CardinalDirection(d) => d match {
      case "N" => N
      case "E" => E
      case "W" => W
      case "S" => S
    }
      
    case RelativeDirection(d) => d match {
      case "F" => F
      case "L" => L
      case "B" => B
      case "R" => R
    }
  }
}
