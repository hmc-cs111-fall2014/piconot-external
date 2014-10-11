package piconot.semantics

import org.scalatest.FunSpec
import picolib.semantics._
import piconot.ir._
import edu.hmc.langtools._
import piconot.parser.PiconotParser

class SemanticsTest extends FunSpec with LangInterpretMatchers[AST,Rule] {
  override val parser = PiconotParser.apply _
  override val interpreter = eval _

  describe("A rule") {
    it("can parse a simple command with one option") {
      program("If you are on First St. and you can go uptown go uptown on First St.") should compute(
        Rule(State("0"),
          Surroundings(Open, Anything, Anything, Anything),
          North,
          State("0")))
    }

    it("can parse a simple command with different states") {
      program("If you are on First St. and you can go uptown go uptown on Second St.") should compute(
        Rule(State("0"),
          Surroundings(Open, Anything, Anything, Anything),
          North,
          State("1")))
    }

    it("can parse a simple command with different states part 2") {
      program("If you are on Second St. and you can go uptown go uptown on First St.") should compute(
        Rule(State("1"),
          Surroundings(Open, Anything, Anything, Anything),
          North,
          State("0")))
    }

    it("can parse a simple command with staying") {
      program("If you are on First St. and you can go uptown teleport to First St.") should compute(
        Rule(State("0"),
          Surroundings(Open, Anything, Anything, Anything),
          StayHere,
          State("0")))
    }

    it("can parse a command with four options") {
      program("If you are on First St. and you can go uptown and you cannot go downtown and you cannot go into_town and you can go outta_town go uptown on First St.") should compute(
        Rule(State("0"),
        Surroundings(Open, Open, Blocked, Blocked),
        North,
        State("0"))
      )
    }
  }
}
