package dsl.picobot.parser

import org.scalatest._

import dsl.picobot.ir._
import dsl.picobot.parser._
import edu.hmc.langtools._

class PicoParserTests extends FunSpec with LangParseMatchers[AST] {
	// stolen from external-lab code
  override val parser = PicoParser.apply _
  
  describe("A program") {

    it("can be two states and an equal sign") {
      program("Proof. Recall empty.txt. Consider 1 = 2. QED") should parseAs ( 
          Program(Declaration("empty.txt"), Consider(List(
             Rule(Lhs(State(1), List.empty), 
        		  	Rhs(State(2), Stay())
        		  ) 
             ))))
    }
    
    it ("can have multiple rules") {
      program("Proof. Recall empty.txt. Consider 1 + n = 2, 1 + n - w * s = 2, 2 = 2 - w. QED") should parseAs(
          Program(Declaration("empty.txt"), Consider(List(
              Rule(Lhs(State(1), List(Plus(N()))),
                   Rhs(State(2), Stay())),
              Rule(Lhs(State(1), List(Plus(N()), Minus(W()), Mult(S()))),
                   Rhs(State(2), Stay())),
    		  Rule(Lhs(State(2), List.empty),
    		       Rhs(State(2), W()))
          )))) 
    }
    
    it ("can have rules with greek letters") {
      program("Proof. Recall empty.txt. Consider 1 + η = 2, 1 + n - ω * ς = 2, 2 = 2 - ε.") should parseAs(
          Program(Declaration("empty.txt"), Consider(List(
              Rule(Lhs(State(1), List(Plus(N()))),
                   Rhs(State(2), Stay())),
              Rule(Lhs(State(1), List(Plus(N()), Minus(W()), Mult(S()))),
                   Rhs(State(2), Stay())),
    		  Rule(Lhs(State(2), List.empty),
    		       Rhs(State(2), E()))
          )))) 
    }
    
    it ("can have no rules") {
      program("Proof. Recall empty.txt. Consider. QED") should parseAs(
          Program(Declaration("empty.txt"), Consider(List.empty))
      )
    }
    
  }

}