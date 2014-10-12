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
import picolib.semantics.TextDisplay
import picolib.semantics.West
//import scalafx.application.JFXApp




package object semantics {
  def eval(ast: AST): Picobot = ast match {
    case Program(declaration, consider) => {
    	val dec = evalMaze(declaration)
    	val con = evalRules(consider)
    	
    	new Picobot(dec, con)
    }
  }
  
  def evalMaze(ast: AST): Maze = ast match {
    case Declaration(mazename) => {
      Maze(mazename) // TODO: potentially path
    }
  }
  
  def evalRules(ast: AST): List[Rule] = ast match {
    case Consider(rules) => {
      rules.map(evalRule).map((rule) => {
        Rule(rule._1.get, rule._2.get, rule._3.get, rule._4.get) 
      })
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
	      Some(evalSurroundings(dir)), 
	      Some(State(state.n.toString()))
      )
    }
  }
  
  def evalSurroundings(ast: AST): RulePart = ast match {
    case Surrounding(dir)
  }
}