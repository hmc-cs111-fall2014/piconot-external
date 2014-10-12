package priorityBot.semantics

import priorityBot.ir._
import java.io.File
import picolib.maze.Maze
import priorityBot.semantics._
import picolib.semantics._
import scalafx.application.JFXApp

package object semantics {
  def eval(ast: AST): Unit = ast match {
    case Priobot(mazeName, ruleList: AST) => 
      object PiconotWithMaze extends JFXApp {
        val maze = Maze("resources" + File.separator + mazeName)
        
        // Explicitly cast the evaluator's Unit return type to a list of rules
        val rules = eval(ruleList).asInstanceOf[List[Rule]]
        
          
	    object Bot extends picolib.semantics.Picobot(maze, rules)
	  		with TextDisplay with GUIDisplay
	  		
	    stage = Bot.mainStage
      }
    case Rules(ruleList) =>
      // TAKE CARE OF *, keep track of previously used directions
      ruleList map (eval)
    case PrioRule(cardinal, d1, d2, d3, d4) =>
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
