package dsl.picobot

import dsl.picobot.ir._

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
import picolib.semantics.MoveDirection
import picolib.semantics.RelativeDescription
import picolib.semantics.TextDisplay
import picolib.semantics.West
import picolib.semantics.StayHere





package object semantics {
  def eval(ast: AST): Picobot = ast match {
    case Program(declaration, consider) => {
    	val dec = evalMaze(declaration)
    	val con = evalRules(consider)
    	
    	new Picobot(dec, con)
    }
    
    case _ => {
      throw new MatchError("Malformed Rule Found.")
    }
  }
  
  def evalMaze(ast: AST): Maze = ast match {
    case Declaration(mazename) => {
      Maze(mazename) // TODO: potentially path
    }
    
    case _ => {
      throw new MatchError("Malformed Rule Found.")
    }
  }
  
  def evalRules(ast: AST): List[Rule] = ast match {
    case Consider(rules) => {
      rules.map(evalRule).map((rule) => {
        Rule(rule._1.get, rule._2.get, rule._3.get, rule._4.get) 
      })
    }
    
    case _ => {
      throw new MatchError("Malformed Rule Found.")
    }
  }    
  
  type RulePart = (
      Option[State], 
      Option[Surroundings], 
      Option[MoveDirection], 
      Option[State]
      )
  
  def evalRule(ast: AST): RulePart = ast match {
    case dsl.picobot.ir.Rule(lhs, rhs) => {
      val lhsrule = evalRule(lhs)
      val rhsrule = evalRule(rhs)
      (lhsrule._1, lhsrule._2, rhsrule._3, rhsrule._4)      
    }
    case Lhs(state, surrounding) => {
      (
          Some(State(state.n.toString())), 
	      Some(evalSurroundings(surrounding)), 
	      None, 
	      None
      )
    }
    case Rhs(state, dir) => {
      (
	      None, None,
	      Some(evalMove(dir)), 
	      Some(State(state.n.toString()))
      )
    }
    case _ => {
      throw new MatchError("Malformed Rule Found.")
    }
  }
  
  
  def evalSurroundings(surrList: List[Surrounding]): Surroundings = {
    val surroundings = surrList.map(surr => evalSurrounding(surr))
    surroundings.fold(Surroundings(Anything, Anything, Anything, Anything))((first, second) => { 
		  val north = if (first.north == Anything) second.north else first.north
  		  val east = if (first.east == Anything) second.east else first.east
		  val south = if (first.south == Anything) second.south else first.south
		  val west = if (first.west == Anything) second.west else first.west
		  Surroundings(north, east, west, south)
		})
  }

  
  def evalSurrounding(surrounding: Surrounding): Surroundings = surrounding match {
    case Plus(dir) => evalDir(dir, Blocked)
    case Minus(dir) => evalDir(dir, Blocked)
    case Mult(dir) => evalDir(dir, Blocked)
  }
  
  def evalDir(dir: Dir, word: RelativeDescription): Surroundings = dir match {
    case N() => Surroundings(word, Anything, Anything, Anything)
    case E() => Surroundings(Anything, word, Anything, Anything)
    case S() => Surroundings(Anything, Anything, Anything, word)
    case W() => Surroundings(Anything, Anything, word, Anything)
    case Stay() => {
      throw new MatchError("Stay() does not make sense in this context.")
    }
  }
  
  def evalMove(dir: Dir): MoveDirection = dir match {
    case N() => North
    case E() => East
    case S() => South
    case W() => West
    case Stay() => StayHere
  }
}