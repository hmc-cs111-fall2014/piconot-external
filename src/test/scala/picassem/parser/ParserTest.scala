package picassem.parser

import org.scalatest._

import picassem.ir._
import picassem.parser._
import edu.hmc.langtools._

class PicAssemblerTests extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = PicParser.apply _
  
  describe("Jump") {
    
    it("is a valid line") {
      program("JMP") should parseAs(Jump())
    }

  }
  
  describe("JumpNext") {
    
    it("is a valid line") {
      program("JMPNE") should parseAs(JumpNext())
    }

  }
  
  describe("Move") {
   
    it("takes in two strings: a register and a binary number") {
      program("MOV \"reg\", \"0001100\"") should parseAs(Move("\"reg\"",12))
    }
    
  }
  
  describe("Begin") {
   
    it("takes in a filename (string)") {
      program("BEGIN \"empty.txt\"") should parseAs(Begin("\"empty.txt\""))
    }
    
  }
  
  describe("End") {
   
    it("takes in nothing") {
      program("END") should parseAs(End())
    }
    
  }
  
}