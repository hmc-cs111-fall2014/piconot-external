package piconot.parser

import org.scalatest._
import edu.hmc.langtools._
import piconot.ir._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  override val parser = PiconotParser.apply _

  describe("A command") {

    it("can parse a simple command with one option") {
      program("If you are on a St. and you can go uptown go uptown on a St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("a"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
           PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("a"), PicoModifier("St.")))))
    }

    it("can parse a simple command with one other option") {
      program("If you are on a St. and you cannot go downtown go uptown on a St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("a"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("a"), PicoModifier("St.")))))
    }

    it("can parse a command with two options") {
      program("If you are on a St. and you can go uptown and you can go downtown go uptown on a St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("a"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
            GetSurroundings(PicoAbility("can"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("a"), PicoModifier("St.")))))
    }

  }
}
