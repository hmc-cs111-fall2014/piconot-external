package piconot.semantics

import piconot.ir._
import piconot._

import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{North => PicoNorth}
import picolib.semantics.{East => PicoEast}
import picolib.semantics.{West => PicoWest}
import picolib.semantics.{South => PicoSouth}
import picolib.semantics.{StayHere => PicoHalt}
import picolib.semantics.{RelativeDescription => PicoDescription}
import picolib.semantics.{MoveDirection => PicoMoveDirection}
import picolib.semantics.Anything;
import picolib.semantics.Open;

package object semantics {
  def eval(ast: AST): PicoRule = ast match {
    case Rule(state1, surr, mov, state2) ⇒ 
    new PicoRule(PicoState(state1.n.toString),
    						   PicoSurroundings(translateSurrComp(surr.north), 
    								   			translateSurrComp(surr.east), 
    								   			translateSurrComp(surr.west), 
    								   			translateSurrComp(surr.south)),
    						   translateMovDir(mov.dir),
    						   PicoState(state2.n.toString))
  }
  
  def translateSurrComp(input: SurroundingComponentType): PicoDescription =  input match {
    case Blocked => PicoBlocked 
    case Wildcard => Anything
    case Free => Open
  }
  
  def translateMovDir(input: MoveDirectionType): PicoMoveDirection = input match {
    case MoveNorth => PicoNorth
    case MoveEast => PicoEast
    case MoveWest => PicoWest
    case MoveSouth => PicoSouth
    case Halt => PicoHalt
  }
}