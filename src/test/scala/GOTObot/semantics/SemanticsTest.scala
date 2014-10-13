package GOTObot.semantics

import org.scalatest._
import picolib.semantics._
import GOTObot.ir._
import GOTObot.parser._
import GOTObot.semantics._
import edu.hmc.langtools._

class GOTOSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, List[Rule]] {

  override val parser = GOTOParser.apply _
  override val interpreter = eval _

  val GOs = Seq("GO", "Go", "G0")
  val TOs = Seq("To", "TO", "T0")

  def setOfRules(state: Int, front: RelativeDescription, move: Boolean, nextState: Int): List[Rule] = {
    var rules: List[Rule] = List.empty
    for(addition <- 0 to 3) {
      rules = rules :+ ruleGen(state*4 + addition, front, move, nextState*4 + addition)
    }

    return rules
  }

  def ruleGen(state: Int, front: RelativeDescription, move: Boolean, nextState: Int): Rule = {
    val dirs = state % 4 match {
      case 0 => (Surroundings(front, Anything, Anything, Anything), North)
      case 1 => (Surroundings(Anything, Anything, front, Anything), West)
      case 2 => (Surroundings(Anything, Anything, Anything, front), South)
      case 3 => (Surroundings(Anything, front, Anything, Anything), East)
    }

    Rule(State(state.toString), dirs._1, if(move) dirs._2 else StayHere, State(nextState.toString))
  }

  describe("A program") {
    it("can be a single rule") {
      val rules = setOfRules(0, Open, true, 0)
       for(rule <- rules ::: setOfRules(0, Anything, false, 1)) {
        println(rule)
      }

      program("GOTO 0") should compute(rules ::: setOfRules(0, Anything, false, 1))
    }


  }
}

/*  describe("A number") {

    it("should evaluate to an integer") {
      program("1") should compute (1)
      program("10") should compute (10)
      program("121") should compute (121)
      program("-10") should compute (-10)
    }

  }

  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should compute (103)
    }

    it("can handle negative numbers") {
      program("1 + -1") should compute (0)
    }

  }

  describe("Subtraction") {

    it("can subtract two numbers") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }

  describe("Multiplication") {

    it("can multiply two numbers") {
      program("3*5") should compute ( 15 )
    }

    it("can be chained (and is left-associative)") {
      program("2 * 4 * 100") should compute ( 800 )
    }

    it("can handle negative numbers") {
      program("1 * -1") should compute ( -1 )
    }

  }

  describe("Division") {

    it("can divide two numbers") {
      program("6 / 3") should compute (2)
    }

    it("performs integer division") {
      program("6 / 5") should compute (1)
    }

    it("can be chained (and is left-associative)") {
      program("100 / 10 / 5") should compute (2)
    }

    it("can handle negative numbers") {
      program("10 / -2") should compute ( -5 )
    }

  }

  describe("Parenthetical Expressions") {

    it("can put one number in parens") {
      program("(1)") should compute (1)
    }

    it("can put addition in parens") {
      program("(1 + 1)") should compute (2)
    }

    it ("can nest parens") {
      program("((1))") should compute (1)
    }

    it ("can put chain in parens") {
      program("(2 * 4 * 100)") should compute (800)
    }

    it ("can reorder using parens") {
      program("100 / (10 / 5)") should compute (50)
    }
  }

  describe("General") {

    it("obeys order of operations") {
      program("3 + 4 * 5") should compute (23)
    }

    it("can use parens to reorder operations") {
      program("(3 + 4) * 5") should compute (35)
    }
  }
 */
