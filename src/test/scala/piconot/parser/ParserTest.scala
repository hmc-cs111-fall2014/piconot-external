package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class CalcParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = PiconotParser.apply _
  
  describe("Partial Rules") {

    it("Partial left-hand sides should not parse") {
      program("0 x***") should not (parse)
    }
    
    it ("Partial right-hand sides should not parse") {
      program("-> N") should not (parse)
    }

  }
  
  describe("Complete Rule") {

    it("Should parse") {
      program("0 x*** -> N 0") should parseAs(Rule(State(0), Surroundings('x', '*', '*', '*'), North, State(0)))
    }

  }
 
}
