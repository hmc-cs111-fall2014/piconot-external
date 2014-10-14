package pico_ought.semantics

import org.scalatest._

import pico_ought.ir._
import pico_ought.parser._
import pico_ought.semantics._
import pico_ought.semantics.helpers._
import picolib.semantics._



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

    it("Generates rules for go with a while") {
        program("Go forwards while open in front.") should compute (
          genGo(FORWARDS, Some(Map(FORWARDS -> Open))))

        program("Go left while open behind and wall on left.") should compute (
          genGo(LEFTWARDS, Some(Map(BACKWARDS -> Open, LEFTWARDS -> Blocked))))
    }

    it("Generates rules for go all the way <dir>") {
        program("Go all the way forwards.") should compute (
          genGo(FORWARDS, Some(Map(FORWARDS -> Open))))
    }

  }

  describe("If <condition>, then <command>") {
    it("Generates rules for a simple if command") {
        program("start: If wall in front, then go forwards once.") should compute (
            genIf(Map(FORWARDS -> Blocked), 1, 1) ++ genGo(FORWARDS, None, 1, 2)
            )
    }

    it("Generates rules for a complex if command") {
        program("start: If wall on right and open in front, then turn right.") should compute (
            genIf(Map(RIGHTWARDS -> Blocked, FORWARDS -> Open), 1, 1) ++ genTurn(R, 1, 2)
            )
    }
  }

  describe("A pico_ought program") {
    it ("Should generate no rules for an empty program") {
        program("") should compute ( List() )
        program("I'm all alone:") should compute ( List() )
        program("start: end:") should compute ( List() )
    }

    it("Should generate rules for a single section program") {
        program("""
            start: 
              Face up.
          """) should compute ( genFace(UP, 1, 1) )

        program("""
            start: 
              Face up.
              Turn right.
          """) should compute ( genFace(UP, 1, 1) ++ genTurn(R, 1, 2) )
    }

    it("Should generate rules for multiple sections") {
        program("""
                start: Face up.
                end: Go forwards once.
            """) should compute ( genFace(UP, 1, 1) ++ genGo(FORWARDS, None, 2, 1) )
    }

    it("Should generate rules for multiple sections with multiple lines") {
        program("""
                start: Go forwards once. Do end.
                middle:
                end: Turn left. Do start.
            """) should compute (
                genGo(FORWARDS, None, 1, 1) ++ 
                genDo("end", List("start", "middle", "end"), 1, 2) ++
                genTurn(L, 3, 1) ++
                genDo("start", List("start", "middle", "end"), 3, 2)
            )
    }

    it("Should solve Right Hand Rule Maze") {
        program( """
                 rhr:
                 Go forwards while open in front and wall on right.
                 If open on right, then do trgfo.
                 Turn left. Do rhr.

                 trgfo:
                 Turn right. Go forwards once. Do rhr.
            """) should compute (
                genGo(FORWARDS, Some(Map(FORWARDS -> Open, RIGHTWARDS -> Blocked)), 1, 1) ++
                genIf(Map(RIGHTWARDS -> Open), 1, 2) ++
                genDo("trgfo", List("rhr", "trgfo"), 1, 3) ++
                genTurn(L, 1, 4) ++
                genDo("rhr", List("rhr", "trgfo"), 1, 5) ++
                genTurn(R, 2, 1) ++
                genGo(FORWARDS, None, 2, 2) ++
                genDo("rhr", List("rhr", "trgfo"), 2, 3)
            )
    }

  }

  // describe("Do a section command") {

  //   it("Generate rules for doing a section") {
  //       program("Do 42.") should compute ( genDo("42", ) )
  //   }

  // }
}