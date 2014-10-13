package priorityBot.semantics

import org.scalatest._
import priorityBot.ir._
import priorityBot.parser._
import priorityBot.semantics._
import edu.hmc.langtools._
import picolib.semantics._
import picolib.maze.Maze
import java.io.File

class SemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Picobot] {

  override val parser = BotParser.apply _
  override val interpreter = eval _

  describe("Solving an Right hand rule maze") {

    it("should evaluate to valid syntax in our internal DSL") {
      program("maze = maze.txt\n* -> R F L B") should compute (
	      new Picobot(Maze("resources" + File.separator + "maze.txt"),
	    		  	  List(	N ->(R, F, L, B),
							E ->(R, F, L, B),
							W ->(R, F, L, B),
							S ->(R, F, L, B)).flatten))
    }
  }
  
  describe("Solving the empty maze") {
    it("") {
      program("maze = empty.txt\nN -> F R L B\nS -> F L B R\nE -> R L F B\nW -> F L R B") should compute (
	      new Picobot(Maze("resources" + File.separator + "empty.txt"),
	    		  	  List(	N ->(F, R, L, B),
							S ->(F, L, B, R),
							E ->(R, L, F, B),
							W ->(F, L, R, B)).flatten))
    }
  }
}