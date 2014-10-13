package piconot.semantics

import org.scalatest._

import piconot.ir._
import piconot.parser._
import piconot.semantics.semantics._
import edu.hmc.langtools._

import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{North => PicoNorth}


class PicoSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, PicoRule] {

  override val parser = PiconotParser.apply _
  override val interpreter = eval _

  describe("Proper interpretation") {
    it("should work a little") {
	  program("0 **** -> W 0") should compute (PicoRule(PicoState("0"), PicoSurroundings(PicoBlocked, PicoBlocked, PicoBlocked, PicoBlocked), PicoNorth, PicoState("0")))
    }
  }

}
