package GOTObot.parser

import org.scalatest._

import GOTObot.ir._
import GOTObot.ir.GO._
import GOTObot.ir.TO._
import GOTObot.parser._
import edu.hmc.langtools._

class GOTObotParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = GOTOParser.apply _

  describe("A program") {
    it("can be a single rule") {
      program("GO TO 0") should parseAs ( Rule(o,F,0) )// -> F (0) )
    }
  }
/*  
  describe("A number") {

    it("can be a single digit") {
      program("1") should parseAs ( 1 )
    }
    
    it ("can be multiple digits") {
      program("10") should parseAs ( 10 )
      program("121") should parseAs ( 121 )
    }
    
    it ("can be a negative number") {
      program("-10") should parseAs ( -10 )
    }
    
    it ("cannot be floating-point number") {
      program("1.1") should not (parse)
      program(" .3") should not (parse)
    }

  }
  
  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should parseAs ( 1 |+| 1 )
    }
    
    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ( (1 |+| 2) |+| 100 )
    }

    it("can handle negative numbers") {
      program("1 + -1") should parseAs ( 1 |+| -1 )
    }

  }

  describe("Subtraction") {

    it("can subtract two numbers") {
      program("1-1") should parseAs ( 1 |-| 1 )
    }
 
    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should parseAs ( (1 |-| 2) |-| 100 )
    }

    it("can handle negative numbers") {
      program("1 - -1") should parseAs ( 1 |-| -1 )
    }

  }

  describe("Multiplication") {

    it("can multiply two numbers") {
      program("3*5") should parseAs ( 3 |*| 5 )
    }
 
    it("can be chained (and is left-associative)") {
      program("2 * 4 * 100") should parseAs ( (2 |*| 4) |*| 100 )
    }

    it("can handle negative numbers") {
      program("1 * -1") should parseAs ( 1 |*| -1 )
    }
    
  }

  describe("Division") {

    it("can divide two numbers") {
      program("6 / 3") should parseAs ( 6 |/| 3 )
    }
 
    it("can be chained (and is left-associative)") {
      program("100 / 10 / 5") should parseAs ( (100 |/| 10) |/| 5 )
    }

    it("can handle negative numbers") {
      program("10 / -2") should parseAs ( 10 |/| -2 )
    }
    
  }

  describe("Parenthetical Expressions") {
    
    it("can put one number in parens") {
      program("(1)") should parseAs (Paren(1))
    }

    it("can put addition in parens") {
      program("(1 + 1)") should parseAs (Paren(1 |+| 1))
    }

    it ("can nest parens") {
      program("((1))") should parseAs (Paren(Paren(1)))
    }

    it ("can put chain in parens") {
      program("(2 * 4 * 100)") should parseAs (Paren( (2 |*| 4) |*| 100 ))
    }

    it ("can reorder using parens") {
      program("100 / (10 / 5)") should parseAs (100 |/| Paren(10 |/| 5))
    }
  }

  describe("General") {
  
    it("obeys order of operations") {
      program("3 + 4 * 5") should parseAs (3 |+| (4 |*| 5))
    }

    it("can use parens to reorder operations") {
      program("(3 + 4) * 5") should parseAs(Paren(3 |+| 4) |*| 5)
    }
  }
*/
}
