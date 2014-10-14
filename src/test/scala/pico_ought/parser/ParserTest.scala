package pico_ought.parser

import org.scalatest._
import picolib.semantics._

import pico_ought.ir._
import pico_ought.parser._
import edu.hmc.langtools._

class PicoOughtParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = PicoOughtParser.apply _

    describe("A Face command") {
        it("Should parse Face <absolute dir>") {
            program("Face up.") should parseAs ( Face(UP) )
            program("Face right.") should parseAs ( Face(RIGHT) )
            program("Face down.") should parseAs ( Face(DOWN) )
            program("Face left.") should parseAs ( Face(LEFT) )
        }
    }

    describe("A Turn command with lots of space") {
        it("Should parse Turn <relative dir>") {
            program("Turn left.") should parseAs ( Turn(L) )
            program("Turn      right.") should parseAs ( Turn(R) )
            program("Turn  \n around.") should parseAs ( Turn(AROUND) )
        }
    }

    describe("A Do command") {
        it("Should parse Do <section>") {
            program("Do   !poop.") should parseAs ( Do("!poop") )
            program("Do poopy poop.") should parseAs ( Do("poopy poop") )
            program("Do p$oop  y p!oop.") should parseAs ( Do("p$oop  y p!oop") )
        }

        it("Should not parse bad labels") {
            program("Do #poop.") should not (parse)
            program("Do p.oop.") should not (parse)
            program("Do p,oop.") should not (parse)
            program("Do poo:p.") should not (parse)
        }
    }

    describe("A Go command") {
        it("Should parse Go <dir> once") {
            program("Go forwards once.") should parseAs ( Go(FORWARDS, None) )
            program("Go right once.") should parseAs ( Go(RIGHTWARDS, None) )
            program("Go backwards once.") should parseAs ( Go(BACKWARDS, None) )
            program("Go left once.") should parseAs ( Go(LEFTWARDS, None) )
        }

        it("Should parse Go <dir> while <condition>") {
            program("Go left while open in front.") should parseAs ( Go(LEFTWARDS, Some(Map(FORWARDS -> Open))) )

            program("Go backwards while wall on left and open behind and wall on right.") should parseAs ( 
                            Go(BACKWARDS, Some(Map(LEFTWARDS -> Blocked, 
                                                BACKWARDS -> Open,
                                                 RIGHTWARDS -> Blocked))))
        }

        it("Should parse Go all the way <dir>") {
            program("Go all the way forwards.") should parseAs ( Go(FORWARDS, Some(Map(FORWARDS -> Open))) )
            program("Go all the way right.") should parseAs ( Go(RIGHTWARDS, Some(Map(RIGHTWARDS -> Open))) )
            program("Go all the way backwards.") should parseAs ( Go(BACKWARDS, Some(Map(BACKWARDS -> Open))) )
            program("Go all the way left.") should parseAs ( Go(LEFTWARDS, Some(Map(LEFTWARDS -> Open))) )
        }
    }

    describe("A Program") {
        it("Should parse empty sections") {
            program("start:") should parseAs ( Program(List(Section("start", List()))) )
            program("A simple section:") should parseAs ( Program(List(Section("A simple section", List()))) )
            program("0!:") should parseAs ( Program(List(Section("0!", List()))) )
            program("Turn right:") should parseAs ( Program(List(Section("Turn right", List()))) )
        }

        it("Should parse multiple empty sections") {
            program("start: end:") should parseAs( (Program(List(Section("start", List()), Section("end", List())))) )
            program("""
                start: 
                end:
                """) should parseAs( (Program(List(Section("start", List()), Section("end", List())))) )
        }

        it("Should not parse bad section names") {
            program(" :") should not (parse)
            program("hey#there:") should not (parse)
            program("hey,there:") should not (parse)
            program("a.section:") should not (parse)
        }

        it("Should parse a single section with a single command") {
            program("""
                Face up:
                    Face up.
                """) should parseAs ( Program(List(Section("Face up", List( Face(UP) )))) )
        }

        it("Should parse multiple simple sections") {
            program("""
                start:
                    Face up.
                end:
                    Face down.
                """) should parseAs ( Program(List(Section("start", List( Face(UP) )),
                                           Section("end", List( Face(DOWN))))) )
        }

        it("Should parse multiple sections with multiple lines") {
            program("""
                start:
                    Turn right. Do end.
                end:
                    Go forwards once. Do start.
                """) should parseAs (
                  Program(List(Section("start", List( Turn(R), Do("end") )),
                               Section("end", List( Go(FORWARDS, None), Do("start") )))) )
        }


    }

    describe("Programs with If commands") {
        it("Should parse a simple If command") {
            program("start: If wall in front, then go forwards once.") should parseAs (
                    Program(List(Section("start", List(If(Map(FORWARDS -> Blocked)), Go(FORWARDS, None)))))
                )
        }

        it("Should parse a complex If command") {
            program("start: If wall on right and open in front, then do your homework.") should parseAs (
                    Program(List(Section("start", List(If(Map(RIGHTWARDS -> Blocked, FORWARDS -> Open)), Do("your homework")))))
                )
        }

        it("Should not parse bad if statements") {
            program("start: If, then.") should not (parse)
            program("start: If, then do something.") should not (parse)
            program("start: If wall on right, then.") should not (parse)
            program("start: If wall on right then do nothing.") should not (parse)
        }
    }

    describe("Programs with  comments") {
        it("Should ignore comments (# <comment> #") {
            program("""
                # The start #
                start:
                    Turn right. # Do start. not! # Do end.
                end:
                    Go forwards once. Do start.
                """) should parseAs (
                  Program(List(Section("start", List( Turn(R), Do("end") )),
                               Section("end", List( Go(FORWARDS, None), Do("start") )))) )

        }

    }


}
