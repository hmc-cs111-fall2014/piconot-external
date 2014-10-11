package piconot.parser

import org.scalatest._
import edu.hmc.langtools._
import piconot.ir._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  override val parser = PiconotParser.apply _

  describe("A command") {

    it("can parse a simple command with one option") {
      program("If you are on First St. and you can go uptown go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
           PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("First"), PicoModifier("St.")))))
    }

    it("can parse a simple command with one other option") {
      program("If you are on First St. and you cannot go downtown go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("First"), PicoModifier("St.")))))
    }

    it("can parse a simple command with two options") {
      program("If you are on First St. and you can go uptown and you cannot go downtown go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
          GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("First"), PicoModifier("St.")))))
    }

    it("can parse a command with three options") {
      program("If you are on First St. and you can go uptown and you cannot go downtown and you cannot go into_town go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
            GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
              GetSurroundings(PicoAbility("cannot"), PicoDirection("into_town"),
              PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))))),
          GetAction(PicoDirection("uptown"),GetStreet(PicoString("First"), PicoModifier("St.")))))
    }


  }
}
