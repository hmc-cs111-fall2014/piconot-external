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
  
  describe("Comp") {
    it("takes in two strings: a register and a binary number") {
      program("CMP \"reg\", \"0001100\"") should compute ()
    }
  }
  
  describe("And") {
    it("takes in two registers and a binary number") {
      program("AND \"reg1\", \"reg2\", \"1100\"") should compute ()
    }
  }
  
}