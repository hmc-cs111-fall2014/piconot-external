package priorityBot

import priorityBot.ir._
import java.io.File
import picolib.maze.Maze
import priorityBot.semantics._
import picolib.semantics._
import scalafx.application.JFXApp

package object semantics {
  def eval(ast: AST): Picobot with TextDisplay with GUIDisplay = ast match {
    case Priobot(mazeName, ruleList: AST) => 
      val maze = Maze("resources" + File.separator + mazeName)
      val rules = // Explicitly cast the evaluator's Unit return type to a list of rules
          evalRules(ruleList)
      object Bot extends Picobot(maze, rules)
	  		with TextDisplay with GUIDisplay 		
	  Bot
  }  
   
  def evalRules(ast: AST): List[Rule] = ast match {
    case Rules(ruleList) =>
      ruleList.flatMap(evalRule).flatten
  }
   
  def evalRule(ast: AST): List[List[Rule]] = ast match {
    case PrioRule(cardinal, d1, d2, d3, d4) => {
      // Evaluate all directions, 
      val dir1 = evalRelative(d1)
      val dir2 = evalRelative(d2)
      val dir3 = evalRelative(d3)
      val dir4 = evalRelative(d4)
      val d = evalCardinal(cardinal)
      
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
  }
  
  def evalCardinal(ast: AST): CardinalState = ast match {
    case CardinalDirection(d) => d match {
      case "N" => N
      case "E" => E
      case "W" => W
      case "S" => S
      case "*" => AnyDir
    }
  }
  
  def evalRelative(ast: AST): RelativeDirection = ast match {
    case RelativeDirection(d) => d match {
      case "F" => F
      case "L" => L
      case "B" => B
      case "R" => R
    }
  }
}
