package piconot.semantics

import org.scalatest._

import piconot.ir._
import piconot.parser._
import piconot.semantics.semantics._
import edu.hmc.langtools._


class NumSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, PicobotProgram] {

  override val parser = PiconotParser.apply _
  override val interpreter = eval _

  describe("A") {

  }

}
