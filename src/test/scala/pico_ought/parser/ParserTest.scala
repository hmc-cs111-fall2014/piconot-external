package pico_ought.parser

import org.scalatest._

import pico_ought.ir._
import pico_ought.parser._
import edu.hmc.langtools._

class PicoOughtParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = PicoOughtParser.apply _
  
  describe("A Face command") {

    it("Face up (i.e. north) ") {
      program("Face up.") should parseAs ( Face(UP) )
      program("Face right.") should parseAs ( Face(RIGHT) )
      program("Face down.") should parseAs ( Face(DOWN) )
      program("Face left.") should parseAs ( Face(LEFT) )
    }
 }
