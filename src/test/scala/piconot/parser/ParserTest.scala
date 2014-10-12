package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class PicobotParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = PiconotParser.apply _
  
  describe("Partial Rules should not parse") {

    it("Partial left-hand sides should not parse") {
      program("0 x***") should not (parse)
    }
    
    it ("Partial right-hand sides should not parse") {
      program("-> N") should not (parse)
    }

  }
  
  describe("Incorrectly formatted rules should not parse") {
    it("should not parse a rule with incorrect surroundings order") {
      program("0 NSEx -> X 1") should not (parse)
    }
    
    it("should not parse a rule with repeated surroundings components") {
      program("0 NN** -> X 1") should not (parse)
    }
    
    it("should not parse a rule with incorrect ordering") {
      program("N*** 1 -> 1 S") should not (parse)
    }
    
   it("should not parse a rule with lowercase surroundings letters") {
      program("n*** 1 -> 1 E") should not (parse)
    }
   
   it("should not parse a rule with lowercase direction letters") {
      program("N*** 1 -> 1 e") should not (parse)
    }
    
    it("should not parse rules with unsupported characters") {
      program("B*** 1 -> 1 T") should not (parse)
    }
    
    it("should not parse rules state numbers that are too large") {
      program("B*** 1 -> 19000 T") should not (parse)
    }
    
    it("should not parse rules state numbers that are too small") {
      program("B*** 1 -> -5 T") should not (parse)
    }
  }
  
  describe("Complete Rule") {
    
    it ("should parse a rule with entirely wildcard surroundings") {
      program("0 **** -> S 0") should 
      parseAs (Rule(State(0), Surroundings('*', '*', '*', '*'), MoveDirection('S'), State(0)))
    }
    
    it ("should parse a rule with a halt statement") {
      program("0 NE*x -> X 1") should
      parseAs (Rule(State(0), Surroundings('N', 'E', '*', 'x'), MoveDirection('X'), State(1)))
    }
    
    it ("should parse a rule with all surroundings blocked") {
      program("0 NEWS -> S 1") should
      parseAs (Rule(State(0), Surroundings('N', 'E', 'W', 'S'), MoveDirection('S'), State(1)))
    }
    
    it ("should parse a rule with all surroundings free") {
      program("0 **** -> N 0") should
      parseAs (Rule(State(0), Surroundings('*', '*', '*', '*'), MoveDirection('N'), State(0)))
    }
    
    it ("should parse a rule with mixed surroundings") {
      program("0 NE*x -> N 0") should
      parseAs (Rule(State(0), Surroundings('N', 'E', '*', 'x'), MoveDirection('N'), State(0)))
    }

  }
 
}
