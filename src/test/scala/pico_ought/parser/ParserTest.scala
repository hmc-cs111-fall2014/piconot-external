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

        // it("Should not parse bad labels") {
        //     program("Do #poop.") should not parse
        // }
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
    }
}
