package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = PiconotParser.apply _
  
  describe("A state") {

    it("can have just one rule") {
      program("0{**** -> N, 0}") should parseAs (  extState(
    		  								       extNum(0),
    		  								         extRule(
    		  								           extWalls(extWall("*"),
    		  								             extWall("*"),
    		  								             extWall("*"),
    		  								             extWall("*")
    		  								           ),
    		  								           extDirection("N"),
    		  								           extNum(0)
    		  								         )
    		  								       ))
    }

  }

}