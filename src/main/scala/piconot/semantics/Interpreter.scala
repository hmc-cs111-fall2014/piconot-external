package piconot.semantics

import piconot.ir._

package object semantics {
  def eval(ast: AST): PicobotProgram = ast match {
    case Rule(startState: State, 
    		  surroundings: Surroundings, 
    		  moveDirection: MoveDirection, 
    		  endState: State) ⇒ 
    		  new Rule(startState, surroundings, moveDirection, endState)
  }
}