package piconot.semantics

import piconot.ir._
import piconot._

import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{North => PicoNorth}

package object semantics {
  def eval(ast: AST): PicoRule = ast match {
    case Rule(state1, surr, mov, state2) ⇒ 
    new PicoRule(PicoState(state1.n.toString),
    						   PicoSurroundings(surr., PicoBlocked, PicoBlocked, PicoBlocked),
    						   PicoNorth,
    						   PicoState(state2.n.toString))
  }
  
  // to use a number on its own
  implicit def Blocked2PicoBlocked(b: Blocked): PicoBlocked = PicoBlocked
}