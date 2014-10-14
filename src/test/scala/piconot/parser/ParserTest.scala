package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class PiconotParserTests extends FunSpec with LangParseMatchers[AST] {

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
  
  describe("Complete Rule, using sample file name") {
    
    it ("should parse a rule with entirely wildcard surroundings") {
      program("abc\n0 **** -> S 0") should 
      parseAs (Program("abc.txt", Rules(List(Rule(State(0), Surroundings(Wildcard, Wildcard, Wildcard, Wildcard), MoveDirection(MoveSouth), State(0))))))
    }
    
    it ("should parse a rule with a halt statement") {
      program("abc\n0 NE*x -> X 1") should
      parseAs (Program("abc.txt", Rules(List(Rule(State(0), Surroundings(Blocked, Blocked, Wildcard, Free), MoveDirection(Halt), State(1))))))
    }
    
    it ("should parse a rule with all surroundings blocked") {
      program("abc\n0 NEWS -> S 1") should
      parseAs (Program("abc.txt", Rules(List(Rule(State(0), Surroundings(Blocked, Blocked, Blocked, Blocked), MoveDirection(MoveSouth), State(1))))))
    }
    
    it ("should parse a rule with all surroundings free ") {
      program("abc\n0 xxxx -> N 0") should
      parseAs (Program("abc.txt", 
          Rules(
              List(
                  Rule(State(0), 
                  Surroundings(Free, Free, Free, Free), 
                  MoveDirection(MoveNorth), 
                  State(0)
                      )
                  )
              )
            )
           )
    }
    
    it("should parse a program with multiple rules") {
      program ("abc\n0 xxxx -> N 0\n1 NEWS -> N 1") should
       parseAs (Program("abc.txt", 
          Rules(
              List(
                  Rule(State(0), 
                       Surroundings(Free, Free, Free, Free), 
                       MoveDirection(MoveNorth), 
                       State(0)), 
                       
                  Rule(State(1), 
                       Surroundings(Blocked, Blocked, Blocked, Blocked), 
                       MoveDirection(MoveNorth), 
                       State(1))
                       
            	  )
                )
              )
              
              ) 
    }
    
    /*it ("should parse a rule with mixed surroundings") {
      program("0 NE*x -> N 0") should
      parseAs (Rule(State(0), Surroundings('N', 'E', '*', 'x'), MoveDirection('N'), State(0)))
    }*/

  }
 
}
