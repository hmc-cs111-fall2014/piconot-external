package priorityBot.parser

import org.scalatest._

import priorityBot.ir._
import priorityBot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = BotParser.apply _
		  
  describe("Simple Program") {

    it("can be parsed") {
      program("maze = maze.txt\nN -> F L B R") should parseAs ( Picobot("maze.txt", 
	    		  													Rules(List(Rule(CardinalDirection("N"), 
	    		  																	RelativeDirection("F"), 
	    		  																	RelativeDirection("L"), 
	    		  																	RelativeDirection("B"), 
	    		  																	RelativeDirection("R"))))))
    }

	  it("cannot be parsed") {
	    program("blah") should not (parse)
	  }
  }
}

