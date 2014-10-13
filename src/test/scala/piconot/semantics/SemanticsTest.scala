package piconot.semantics

// Boilerplate imports
import org.scalatest._
import edu.hmc.langtools._

// Import our internal representation, parser, and semantics
import piconot.ir._
import piconot.parser._
import piconot.semantics.semantics._

// Grab and rename elements of the picolib
// Basic Building Blocks
import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}

// Directions
import picolib.semantics.{North => PicoNorth}
import picolib.semantics.{East => PicoEast}
import picolib.semantics.{West => PicoWest}
import picolib.semantics.{South => PicoSouth}

// Surrounding Components
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{Anything => PicoAnything};
import picolib.semantics.{Open => PicoOpen};

class PicoSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, PicoRule] {

  override val parser = PiconotParser.apply _
  override val interpreter = eval _

  describe("Proper interpretation") {
    it("should compute rules with all surroundings blocked") {
	  program("0 NEWS -> W 0") should compute (PicoRule(PicoState("0"), PicoSurroundings(PicoBlocked, PicoBlocked, PicoBlocked, PicoBlocked), PicoWest, PicoState("0")))
    }
    
    it("should compute rules with all surroundings open") {
      program("0 xxxx -> N 1") should compute (PicoRule(PicoState("0"), PicoSurroundings(PicoOpen, PicoOpen, PicoOpen, PicoOpen), PicoNorth, PicoState("1")))
    }
    
    it("should compute rules with mixed surroundings") {
      program("2 xEx* -> S 1") should compute (PicoRule(PicoState("2"), PicoSurroundings(PicoOpen, PicoBlocked, PicoOpen, PicoAnything), PicoSouth, PicoState("1")))
    }
  }

}
