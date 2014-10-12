package priorityBot.semantics

import org.scalatest._

import priorityBot.ir._
import priorityBot.parser._
import priorityBot.semantics._
import edu.hmc.langtools._

class SemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Int] {

  override val parser = BotParser.apply _
  override val interpreter = eval _

//  describe("An empty") {
//
//    it("should evaluate to an integer") {
//      program("1") should compute (1)
//      program("10") should compute (10)
//      program("121") should compute (121)
//      program("-10") should compute (-10)
//    }
//
//  }
}