package priorityBot

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
        
          
	    object Bot extends Picobot(maze, rules)
	  		with TextDisplay with GUIDisplay
	  		
	    stage = Bot.mainStage
      }
      
    case Rules(ruleList) =>
      ruleList.map(eval).asInstanceOf[List[List[Rule]]].flatten
      
    case PrioRule(cardinal, d1, d2, d3, d4) => {
      // Evaluate all directions, 
      val dir1 = eval(d1).asInstanceOf[RelativeDirection]
      val dir2 = eval(d2).asInstanceOf[RelativeDirection]
      val dir3 = eval(d3).asInstanceOf[RelativeDirection]
      val dir4 = eval(d4).asInstanceOf[RelativeDirection]
      val d = eval(cardinal).asInstanceOf[CardinalState]
      
      // Make a list of rules, either a singleton list with a single rule for
      // the specified cardinal direction, or a list of all rules if 'AnyDir' 
      // is given for a cardinal direction
      d match {
      case N|E|W|S => List(d ->(dir1, dir2, dir3, dir4))
      case AnyDir  => List(N ->(dir1, dir2, dir3, dir4),
    		  			   E ->(dir1, dir2, dir3, dir4),
    		  			   W ->(dir1, dir2, dir3, dir4),
    		  			   S ->(dir1, dir2, dir3, dir4)) 
      }
    }
    
    case CardinalDirection(d) => d match {
      case "N" => N
      case "E" => E
      case "W" => W
      case "S" => S
      case "*" => AnyDir
    }
      
    case RelativeDirection(d) => d match {
      case "F" => F
      case "L" => L
      case "B" => B
      case "R" => R
    }
  }
}
