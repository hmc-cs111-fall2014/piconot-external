package picassem

import picassem.ir._
import picolib.maze._
import picolib.semantics._
import java.io.File

package object semantics {
  var state:Int = 0
  var newState:Int = 0
  var surr:Int = 0
  var dir:Int = 0
  var ext:Int = 0
  var cdr:Int = 0
  var cmprslt:Boolean = false
  var isState:Boolean = true // we are at a state rule
  /* which surr we are currently reading */
  var northSurr:Int = 2
  var eastSurr:Int = 2
  var westSurr:Int = 2
  var southSurr:Int = 2
  
  var rules: scala.collection.mutable.MutableList[Rule] = scala.collection.mutable.MutableList()
  
  def eval(ast: AST):Any = ast match {
    case Jump() => {
      var stringDir = dir match {
	    case 0 => North
	    case 1 => East
	    case 2 => West
	    case 3 => South
	    case _ => North
	  }
      
      rules.+=(
              Rule(
               State(state.toString()), 
	           Surroundings(Anything, Anything, Open, Anything), 
	           stringDir,
	           State(newState.toString())
	          )
          )
    }
    
    //TODO:: Turn these into Open, Anything, Closed
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
      }
    }
    
    case Move(reg, bin) => {
      reg.substring(1, reg.length()-1) match {
        case "STAT" => state=bin
        case "SURR" => surr=bin
        case "DIR" => dir=bin
        case "CDR" => cdr=bin
        case _ => ext= -1
      }
    }
    case Comp(reg, bin) => {
      reg.substring(1, reg.length()-1) match {
        case "EXT" => cmprslt = (ext==bin)
        case _ => ext= -1
      }
    }
    case And(reg1, reg2, bin) => {
      reg1.substring(1, reg1.length()-1) match {
        case "EXT" => {
          reg2.substring(1, reg2.length()-1) match {
            case "SURR" => ext = (surr & bin)
            case _ => ext = -1
          }
        }
        case _ => ext= -1
      }
    }
  }
  
  
  
  
  
  
  
}