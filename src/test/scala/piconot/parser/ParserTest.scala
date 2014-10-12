package piconot.parser

import org.scalatest._
import edu.hmc.langtools._
import piconot.ir._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  override val parser = PiconotParser.apply _

  describe("A command") {

    it("can parse a simple command with one option") {
      program("If you are on First St. and can go uptown, go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
           PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetFinalDirection(PicoDirection("uptown")),
          GetFinalStreet(GetStreet(PicoString("First"), PicoModifier("St."))), PicoString("null")))
    }

    it("can parse a simple command with a cannot") {
      program("If you are on Second St. and cannot go downtown, go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("Second"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetFinalDirection(PicoDirection("uptown")),
          GetFinalStreet(GetStreet(PicoString("First"), PicoModifier("St."))), PicoString("null")))
    }

    it("can parse a simple command with two options") {
      program("If you are on First St. and can go uptown and cannot go downtown, go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
          GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))),
          GetFinalDirection(PicoDirection("uptown")),
          GetFinalStreet(GetStreet(PicoString("First"), PicoModifier("St."))), PicoString("null")))
    }

    it("can parse a command with three options") {
      program("If you are on First St. and can go uptown and cannot go downtown and cannot go into_town, go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
            GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
              GetSurroundings(PicoAbility("cannot"), PicoDirection("into_town"),
              PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))))),
          GetFinalDirection(PicoDirection("uptown")),
          GetFinalStreet(GetStreet(PicoString("First"), PicoModifier("St."))), PicoString("null")))
    }

    it("can parse a command with four options") {
      program("If you are on First St. and can go uptown and cannot go downtown and cannot go into_town and can go outta_town, go uptown on First St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("First"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
            GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
              GetSurroundings(PicoAbility("cannot"), PicoDirection("into_town"),
                GetSurroundings(PicoAbility("can"), PicoDirection("outta_town"),
                PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))))),
          GetFinalDirection(PicoDirection("uptown")),
          GetFinalStreet(GetStreet(PicoString("First"), PicoModifier("St."))), PicoString("null")))
    }

    it("can parse multiple commands in the same program") {
      program("If you are on Second St. and can go downtown, go downtown on Fourth St." +
        "If you are on Second St. and cannot go downtown and can go outta_town, go outta_town on Second St." +
        "If you are on Second St. and cannot go downtown and cannot go outta_town and can go uptown, go uptown on Main St." +
        "If you are on Second St. and cannot go downtown and cannot go outta_town and cannot go uptown and can go into_town, go into_town on Third St.") should parseAs(
        MakeCommand(
          GetStreet(PicoString("Second"), PicoModifier("St.")),
          GetSurroundings(PicoAbility("can"), PicoDirection("downtown"),
            PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))),
          GetFinalDirection(PicoDirection("downtown")),
          GetFinalStreet(GetStreet(PicoString("Fourth"), PicoModifier("St."))),
          MakeCommand(
            GetStreet(PicoString("Second"), PicoModifier("St.")),
            GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
              GetSurroundings(PicoAbility("can"), PicoDirection("outta_town"),
                PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))),
            GetFinalDirection(PicoDirection("outta_town")),
            GetFinalStreet(GetStreet(PicoString("Second"), PicoModifier("St."))),
            MakeCommand(
              GetStreet(PicoString("Second"), PicoModifier("St.")),
              GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
                GetSurroundings(PicoAbility("cannot"), PicoDirection("outta_town"),
                  GetSurroundings(PicoAbility("can"), PicoDirection("uptown"),
                    PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null"))))),
              GetFinalDirection(PicoDirection("uptown")),
              GetFinalStreet(GetStreet(PicoString("Main"), PicoModifier("St."))),
              MakeCommand(
                GetStreet(PicoString("Second"), PicoModifier("St.")),
                GetSurroundings(PicoAbility("cannot"), PicoDirection("downtown"),
                  GetSurroundings(PicoAbility("cannot"), PicoDirection("outta_town"),
                    GetSurroundings(PicoAbility("cannot"), PicoDirection("uptown"),
                      GetSurroundings(PicoAbility("can"), PicoDirection("into_town"),
                        PicoSurroundings(PicoAbility("null"), PicoAbility("null"), PicoAbility("null"), PicoAbility("null")))))),
                GetFinalDirection(PicoDirection("into_town")),
                GetFinalStreet(GetStreet(PicoString("Third"), PicoModifier("St."))), PicoString("null")))))
      )
    }


  }
}
