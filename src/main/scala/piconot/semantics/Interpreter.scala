package piconot.semantics

import piconot.ir._

package object semantics {
  def eval(ast: AST): PicobotProgram = ast match {
    case _ => null
  }
}