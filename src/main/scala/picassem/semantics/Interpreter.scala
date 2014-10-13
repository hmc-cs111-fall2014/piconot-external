package picassem

import picassem.ir._
import picolib.maze._
import picolib.semantics._
import java.io.File

package object semantics {
  var state:Int = 0
  var newState:Int = 0
  var dir:Int = 0
  var ext:Int = 0
  var cdr:Int = -1
  /* which surr we are currently reading */
  var northSurr:Int = 2
  var eastSurr:Int = 2
  var westSurr:Int = 2
  var southSurr:Int = 2
  
  var rules: scala.collection.mutable.MutableList[Rule] = scala.collection.mutable.MutableList()
  // default values (should be overridden)
  var mazeFilename = "resources" + File.separator + "empty.txt"                 
  var maze = Maze(io.Source.fromFile(mazeFilename).getLines().toList)
  
  def eval(ast: AST):Any = ast match {
    case Jump() => {
      var stringDir = dir match {
	    case 0 => North
	    case 1 => East
	    case 2 => West
	    case 3 => South
	    case 4 => StayHere
	    case _ => North
	  }
      
      var northString = northSurr match {
        case 0 => Open
        case 1 => Blocked
        case _ => Anything
      }
      var eastString = eastSurr match {
        case 0 => Open
        case 1 => Blocked
        case _ => Anything
      }
      var westString = westSurr match {
        case 0 => Open
        case 1 => Blocked
        case _ => Anything
      }
      var southString = southSurr match {
        case 0 => Open
        case 1 => Blocked
        case _ => Anything
      }
      
      rules.+=(
              Rule(
               State(state.toString()), 
	           Surroundings(northString, eastString, westString, southString), 
	           stringDir,
	           State(newState.toString())
	          )
          )
      
      // setting cdr to -1 to ensure we don't make a bad rule
      cdr= -1
      northSurr = 2
      eastSurr = 2
      westSurr = 2
      southSurr = 2
    }
    
    case JumpNext() => {
      cdr match {
        case 0 => {
          northSurr = if (ext==0) 0 else 1
        }
        case 1 => {
          eastSurr = if (ext==0) 0 else 1
        }
        case 2 => {
          westSurr = if (ext==0) 0 else 1
        }
        case 3 => {
          southSurr = if (ext==0) 0 else 1
        }
        case _ => {}
      }
    }
    
    case Move(reg, bin) => {
      reg.substring(1, reg.length()-1) match {
        case "STAT" => state=bin
        case "DIR" => dir=bin
        case "CDR" => cdr=bin
        case "NEWSTAT" => newState=bin
        case "EXT" => ext=bin
        case _ => ext= -1
      }
    }
    
    case Begin(filename) => {
      val parsedFilename = filename.substring(1, filename.length()-1)
      mazeFilename = "resources" + File.separator + parsedFilename
      maze = Maze(io.Source.fromFile(mazeFilename).getLines().toList)
    }
    
    case End() => {
      val j = new Picobot(maze, rules.toList)
      while (j.canMove) {
        println(j.toString())
        j.step()
      }
    }
  }
  
  
  
  
  
  
  
}