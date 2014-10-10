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

  describe("Turn in direction command") {

    it("Generate rules for turning in a direction") {
      program("Turn left.") should compute ( genTurn(L) )
      program("Turn right.") should compute ( genTurn(R) )
      program("Turn around.") should compute ( genTurn(AROUND) )
    }

  }

  describe("Go in direction command") {

    it("Generates rules for go once in a direction") {
        program("Go forwards once.") should compute ( genGo(FORWARDS, None) )
        program("Go right once.") should compute ( genGo(RIGHTWARDS, None) )
        program("Go backwards once.") should compute ( genGo(BACKWARDS, None) )
        program("Go left once.") should compute ( genGo(LEFTWARDS, None) )
    }

    
  }

  // describe("Do a section command") {

  //   it("Generate rules for doing a section") {
  //       program("Do 42.") should compute ( genDo("42", ) )
  //   }

  // }
}