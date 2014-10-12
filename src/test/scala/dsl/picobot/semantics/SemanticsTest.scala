package picobot.semantics

import org.scalatest._

import dsl.picobot.ir._
import dsl.picobot.parser._
import dsl.picobot.semantics._
import edu.hmc.langtools._

import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West

class PicobotSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Picobot] {
  override val parser = PicoParser.apply _
  override val interpreter = eval _
  
  describe("A picobot program") {

    it("should be able to interpret a single rule") { 
    	program("Proof. Recall empty.txt. Consider 1 = 2.") should compute (    	    
    	    new Picobot(Maze("resources/empty.txt"), List(
    	        picolib.semantics.Rule(State("1"),
    	        	Surroundings(Anything, Anything, Anything, Anything),
    	        	picolib.semantics.StayHere,
    	        	State("2")))))
    }

    it("should be able to interpret multiple rules") {
    	program("Proof. Recall empty.txt. Consider 1 + n = 2, 1 + n - w * s = 2, 2 = 2 - w.") should compute (
    		new Picobot(Maze("resources/empty.txt"), List(
    		    picolib.semantics.Rule(State("1"),
    	        	Surroundings(Blocked, Anything, Anything, Anything),
    	        	picolib.semantics.StayHere,
    	        	State("2")),
    	        picolib.semantics.Rule(State("1"),
    	        	Surroundings(Blocked, Anything, Open, Anything),
    	        	picolib.semantics.StayHere,
    	        	State("2")),
    	        picolib.semantics.Rule(State("2"),
    	        	Surroundings(Anything, Anything, Anything, Anything),
    	        	picolib.semantics.West,
    	        	State("2")))))
    }	
    
    it ("can have no rules") {
      program("Proof. Recall empty.txt. Consider.") should compute (
          new Picobot(Maze("resources/empty.txt"), List.empty)
      )
    }
    
    
  }

  
}
