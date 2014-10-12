package piconot.semantics

import piconot.ir._


package object semantics {
  def eval(ast: AST): PicobotProgram = ast match {
    case Rule(state1, surr, mov, state2) ⇒ 
    	Rule(state1, surr, mov, state2)
  }
}