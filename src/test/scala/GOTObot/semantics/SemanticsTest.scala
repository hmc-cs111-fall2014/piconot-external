package GOTObot.semantics

import org.scalatest._
import picolib.semantics._
import GOTObot.parser._
import GOTObot.semantics._
import GOTObot.ir.AST
import edu.hmc.langtools._
import GOTObot_internal._

// Test using our internal DSL as confirmation
class GOTOSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, List[Rule]] {

  override val parser = GOTOParser.apply _
  override val interpreter = eval _

  def internalRules(code : =>Unit) = {
    GOTObot_internal.GlobalVars.rules = List.empty[Rule]
    code
    GOTObot_internal.GlobalVars.rules
  }

  describe("A program") {
    it("can be a single rule") {
      program("GOTO 0\n") should compute(internalRules {
        GO TO 0
      })
    }
  
    it("can be multiple rules") {
      program("GOTO 0\nGOTO 1\n") should compute(internalRules {
        GO TO 0
        GO TO 1
      })
      program("GOTO0GOTO 1\nGOTO 2\n") should compute(internalRules {
        GO TO 0
        GO TO 1
        GO TO 2
      })
      program("GOTO 0\n\n\nGOTO 1\n") should compute(internalRules {
        GO TO 0
        GO TO 1
      })
    }

    it("supports GO, G0, Go, TO, T0, and To") {
      program("GOTO 0\n") should compute(internalRules {
        GO TO 0
      })
      program("G0TO 0\n") should compute(internalRules {
        G0 TO 0
      })
      program("GoTO 0\n") should compute(internalRules {
        Go TO 0
      })
      program("GOT0 0\n") should compute(internalRules {
        GO T0 0
      })
      program("GOTo 0\n") should compute(internalRules {
        GO To 0
      })
    } 
  }
}
