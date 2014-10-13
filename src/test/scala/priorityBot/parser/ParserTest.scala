package priorityBot.parser

import org.scalatest._

import priorityBot.ir._
import priorityBot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = BotParser.apply _
		  
  describe("Simple Program") {

    it("one rule can be parsed") {
      program("maze = maze.txt\n* -> F L B R") should parseAs ( Priobot("maze.txt", 
	    		  													Rules(List(PrioRule(CardinalDirection("*"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))))))
    }

    it("two rules be parsed") {
      program("maze = maze.txt\nN -> F L B R\nS -> F L B R") should parseAs ( Priobot("maze.txt", 
	    		  													Rules(List(PrioRule(CardinalDirection("N"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))) ++
	    		  														  List(PrioRule(CardinalDirection("S"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))))))
    }

    it("five rules CANNOT be parsed") {
      program("maze = maze.txt\nN -> F L B R\nS -> F L B R\nS -> F L B R\nS -> F L B R\n* -> F L B R") should not (parse)    
    }

	it("arbitrary text cannot be parsed") {
	  program("blah") should not (parse)
	}
	
	it("invalid maze name cannot be parsed") {
      program("maze = maze.bad\n* -> F L B R") should not (parse)
	}
	
	it("bad syntax for arrow in rule cannot be parsed") {
	  program("maze = maze.txt\n* F L B R") should not (parse)
	}
	
	it("less than 4 relative directions should not parse") {
	  program("maze = maze.txt\n* -> F L B") should not (parse)
	}
	
	it("more than 4 relative directions should not parse") {
	  program("maze = maze.txt\n* -> F L B R W") should not (parse)
	}
	
	it("invalid cardinal direction should not parse") {
	  program("maze = maze.txt\nT -> F L B R") should not (parse)
	}
  }
}

