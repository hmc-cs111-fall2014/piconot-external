package dsl.picobot

import dsl.picobot.ir._

package object semantics {
  def eval(ast: AST): Int = ast match {
    case _ => 5
    }
}