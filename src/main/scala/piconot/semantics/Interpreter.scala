package piconot.semantics

import piconot.ir._
import piconot._

import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{North => PicoNorth}
import picolib.semantics.{RelativeDescription => PicoDescription}
import picolib.semantics.Anything;
import picolib.semantics.Open;

package object semantics {
  def eval(ast: AST): PicoRule = ast match {
    case Rule(state1, surr, mov, state2) ⇒ 
    new PicoRule(PicoState(state1.n.toString),
    						   PicoSurroundings(translate(surr.north), translate(surr.east), translate(surr.west), translate(surr.south)),
    						   PicoNorth,
    						   PicoState(state2.n.toString))
  }
  
  def translate(input: SurroundingComponentType): PicoDescription =  input match {
    case Blocked => PicoBlocked 
    case Wildcard => Anything
    case Free => Open
  }
  
  // to use a number on its own
 // implicit def Blocked2PicoBlocked(b: Blocked): PicoBlocked = PicoBlocked
}