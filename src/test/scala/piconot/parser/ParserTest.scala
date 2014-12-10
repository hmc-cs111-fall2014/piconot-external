package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = PiconotParser.apply _
  
  describe("A state") {

    it("can have just one rule") {
      program("0{**** -> N, 0}") should parseAs (extState(
    		  								       extNum(0),
    		  								         extRule(
    		  								           extWalls(
    		  								             extWall("*"),
    		  								             extWall("*"),
    		  								             extWall("*"),
    		  								             extWall("*")
    		  								           ),
    		  								           extDirection("N"),
    		  								           extNum(0)
    		  								         )
    		  								       ))
    }
    
    it("can have multiple rules") {
      program("0{**** -> N, 0  X*** -> E, 0}") should parseAs( extState(
    		  												     extNum(0),
    		  												       extRuleSet(
    		  												         extRule(
    		  												           extWalls(
    		  												             extWall("*"),
    		  												             extWall("*"),
    		  												             extWall("*"),
    		  												             extWall("*")
    		  												           ),
    		  												           extDirection("N"),
    		  												           extNum(0)
    		  												         ),
    		  												         extRule(
    		  												           extWalls(
    		  												             extWall("X"),
    		  												             extWall("*"),
    		  												             extWall("*"),
    		  												             extWall("*")
    		  												           ),
    		  												           extDirection("E"),
    		  												           extNum(0)
    		  												         )
    		  												       )
    		  												       ))
    }

  }
  
  describe("A program") {
    
    it("Is multiple states") {
      program("0{**** -> N, 1} 1{**** -> S, 0}") should parseAs(
          extProgram(
            extState(
              extNum(0),
              extRule(
                extWalls(
                  extWall("*"),
                  extWall("*"),
                  extWall("*"),
                  extWall("*")
                ),
                extDirection("N"),
                extNum(1)
              )
            ),
            extState(
              extNum(1),
              extRule(
                extWalls(
                  extWall("*"),
                  extWall("*"),
                  extWall("*"),
                  extWall("*")
                ),
                extDirection("S"),
                extNum(0)
              )
            )
          ))
    }
    
  }

}