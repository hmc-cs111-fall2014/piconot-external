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
      program("1 = 2") should parseAs ( 
          Equal(
        		  	Lhs(
        		  	    State(1), List.empty
        		  	    ), 
        		  	Rhs(
        		  	    State(2), 
        		  	    Stay()
        		  	    )
        		  ) 
          )
    }
    
    it ("can have directions in lhs") {
      program("1 + n = 2") should parseAs ( 
          Equal(
              Lhs(State(1), List(
                  Plus(N())
                  )
              ), Rhs(State(2), Stay())) )
      program("1 + n - w * s = 2") should parseAs ( Equal(
          Lhs(
        		  State(1), 
        		  List(Plus(N()), Minus(W()), Mult(S()))
    		  ), Rhs(State(2), Stay()) ) )
    }
    
    it ("can have directions in rhs") {
      program("2 = 2 - w") should parseAs ( Equal(Lhs(State(2), List.empty), Rhs(State(2), W())))
    }

  }

}