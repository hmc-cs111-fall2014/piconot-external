package pico_ought.semantics

import org.scalatest._

import pico_ought.ir._
import pico_ought.parser._
import pico_ought.semantics._
import pico_ought.semantics.helpers._



import edu.hmc.langtools._

class NumSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Any] {

  override val parser = PicoOughtParser.apply _
  override val interpreter = eval _

  describe("Face in direction command") {

    it("Generate rules for facing in a cardinal direction") {
      program("Face up.") should compute ( genFace(UP) )
      program("Face right.") should compute ( genFace(RIGHT) )
      program("Face down.") should compute ( genFace(DOWN) )
      program("Face left.") should compute ( genFace(LEFT) )
    }

  }
}