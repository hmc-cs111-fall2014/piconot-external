package priorityBot.parser

import org.scalatest._

import priorityBot.ir._
import priorityBot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = BotParser.apply _
		  
  describe("Simple Program") {

    it("can be parsed") {
      program("maze = maze.txt\n* -> F L B R") should parseAs ( Picobot("maze.txt", 
	    		  													Rules(List(Rule(CardinalDirection("*"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))))))
    }

    it("two rules be parsed") {
      program("maze = maze.txt\nN -> F L B R\nS -> F L B R") should parseAs ( Picobot("maze.txt", 
	    		  													Rules(List(Rule(CardinalDirection("N"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))) ++
	    		  														  List(Rule(CardinalDirection("S"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))))))
    }

    it("five rules CANNOT be parsed") {
      program("maze = maze.txt\nN -> F L B R\nS -> F L B R\nS -> F L B R\nS -> F L B R\n* -> F L B R") should not (parse)    
    }

	it("cannot be parsed") {
	  program("blah") should not (parse)
	}
  }
}

