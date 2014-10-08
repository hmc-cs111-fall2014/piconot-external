package GOTObot.semantics

import org.scalatest._

import GOTObot.ir._
import GOTObot.parser._
import GOTObot.semantics._
import edu.hmc.langtools._

class GOTOSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Int] {

  override val parser = GOTOParser.apply _
  override val interpreter = eval _
/*
  describe("A number") {

    it("should evaluate to an integer") {
      program("1") should compute (1)
      program("10") should compute (10)
      program("121") should compute (121)
      program("-10") should compute (-10)
    }

  }

  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should compute (103)
    }

    it("can handle negative numbers") {
      program("1 + -1") should compute (0)
    }

  }

  describe("Subtraction") {

    it("can subtract two numbers") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }

  describe("Multiplication") {

    it("can multiply two numbers") {
      program("3*5") should compute ( 15 )
    }
 
    it("can be chained (and is left-associative)") {
      program("2 * 4 * 100") should compute ( 800 )
    }

    it("can handle negative numbers") {
      program("1 * -1") should compute ( -1 )
    }
    
  }

  describe("Division") {

    it("can divide two numbers") {
      program("6 / 3") should compute (2)
    }

    it("performs integer division") {
      program("6 / 5") should compute (1)
    }
 
    it("can be chained (and is left-associative)") {
      program("100 / 10 / 5") should compute (2)
    }

    it("can handle negative numbers") {
      program("10 / -2") should compute ( -5 )
    }
    
  }

  describe("Parenthetical Expressions") {
    
    it("can put one number in parens") {
      program("(1)") should compute (1)
    }

    it("can put addition in parens") {
      program("(1 + 1)") should compute (2)
    }

    it ("can nest parens") {
      program("((1))") should compute (1)
    }

    it ("can put chain in parens") {
      program("(2 * 4 * 100)") should compute (800)
    }

    it ("can reorder using parens") {
      program("100 / (10 / 5)") should compute (50)
    }
  }

  describe("General") {
   
    it("obeys order of operations") {
      program("3 + 4 * 5") should compute (23)
    }

    it("can use parens to reorder operations") {
      program("(3 + 4) * 5") should compute (35)
    }
  }
*/
}
