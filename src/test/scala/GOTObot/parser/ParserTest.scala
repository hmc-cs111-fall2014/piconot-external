package GOTObot.parser

import org.scalatest._

import GOTObot.ir._
import GOTObot.ir.GO._
import GOTObot.ir.TO._
import GOTObot.parser._
import edu.hmc.langtools._

class GOTOParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = GOTOParser.apply _

  describe("A program") {
    it("can be a single rule") {
      program("GOTO 0\n") should parseAs ( GOTORule(o,F,0) )
    }

    it("can be multiple rules") {
      program("GOTO 0\nGOTO 1\n") should parseAs ( GOTORule(o,F,0) followedBy GOTORule(o, F, 1))
      program("GOTO 0\nGOTO 1\nGOTO 2\n") should parseAs ( GOTORule(o,F,0) followedBy ( GOTORule(o, F, 1) followedBy GOTORule(o, F, 2)))
      program("GOTO 0\n\n\nGOTO 1\n") should parseAs ( GOTORule(o,F,0) followedBy GOTORule(o, F, 1))
    }

    it("supports GO, G0, Go, TO, T0, and To") {
      program("GOTO 0\n") should parseAs ( GOTORule(o,F,0) )
      program("G0TO 0\n") should parseAs ( GOTORule(*,F,0) )
      program("GoTO 0\n") should parseAs ( GOTORule(x,F,0) )
      program("GOT0 0\n") should parseAs ( GOTORule(o,L,0) )
      program("GOTo 0\n") should parseAs ( GOTORule(o,X,0) )
    }

    it("cannot be anything other than GOTOs") {
      program("GOTOs\n") should not(parse)
      program("GOTO n\n") should not(parse)
      program("1\n") should not(parse)
    }
  }
}
