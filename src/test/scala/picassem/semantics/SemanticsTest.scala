package picassem.semantics

import org.scalatest._

import picassem.ir._
import picassem.parser._
import picassem.semantics._
import edu.hmc.langtools._

class NumSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Any] {

  override val parser = PicParser.apply _
  override val interpreter = eval _
  
  describe("Jump") {
    it("should be a valid line") {
      program("JMP") should compute ()
    }
  }
  
  describe("JumpNext") {
    it("should be a valid line") {
      program("JMPNE") should compute ()
    }
  }
  
  describe("Move") {
    it("takes in two strings: a register and a binary number") {
      program("MOV \"reg\", \"0001100\"") should compute ()
    }
  }
  
  describe("Begin") {
    it("should take in a filename") {
      program("BEGIN \"empty.txt\"") should compute ()
    }
  }
  
  describe("End") {
    it("should be a valid line") {
      program("END") should compute ()
    }
  }
  
}