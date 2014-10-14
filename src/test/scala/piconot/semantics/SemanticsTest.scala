package piconot.semantics

import org.scalatest._

import piconot.parser._
import piconot.semantics._
import piconot.ir._
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.{East => PLEast}
import picolib.semantics.{North => PLNorth}
import picolib.semantics.Open
import picolib.semantics.{Rule => PLRule}
import picolib.semantics.{South => PLSouth}
import picolib.semantics.{State => PLState}
import picolib.semantics.Surroundings
import picolib.semantics.{West => PLWest}
import picolib.semantics.{MoveDirection => PLMoveDirection}
import picolib.semantics.StayHere
import picolib.semantics.RelativeDescription
import edu.hmc.langtools._

/**
 * Note: Per a discussion with Prof. Ben, we are testing our language at the State level,
 * as a State is the smallest unit of code in our language that can be meaningfully
 * translated to Picolib library calls.
 */
class SemanticsTests extends FunSpec
    with LangInterpretMatchers[State, List[PLRule]] {

  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.state, s)}
  override val interpreter = generateRulesForState _

  describe("A valid state") {

    it("can contain one rule") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should compute (List(PLRule(PLState("0"), Surroundings(Open, Blocked, Open, Blocked), StayHere, PLState("4"))))
    }
    
    it("can contain multiple rules") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4" +
              "\n" +
              "\nFREE DIRECTIONS: N, E" +
              "\nBLOCKED DIRECTIONS: S, W" +
              "\nMOVE DIRECTION: S" +
              "\nNEW STATE: 3") should compute (List(PLRule(PLState("0"), Surroundings(Open, Blocked, Open, Blocked), StayHere, PLState("4")),
            		  								 PLRule(PLState("0"), Surroundings(Open, Open, Blocked, Blocked), PLSouth, PLState("3"))))
    }

    it("can contain no free/blocked directions") {
      program("STATE 0:\nFREE DIRECTIONS:" +
              "\nBLOCKED DIRECTIONS:" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should compute (List(PLRule(PLState("0"), Surroundings(Anything, Anything, Anything, Anything), StayHere, PLState("4"))))
    }
    
    it("can handle directions not in NEWS order") {
      program("STATE 0:\nFREE DIRECTIONS: S, N" +
              "\nBLOCKED DIRECTIONS: W, E" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should compute (List(PLRule(PLState("0"), Surroundings(Open, Blocked, Blocked, Open), StayHere, PLState("4"))))
    }

  }
}
