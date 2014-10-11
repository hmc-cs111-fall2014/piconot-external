package piconotexternal.parser

import org.scalatest._

import piconotexternal.ir._
import piconotexternal.parser._
import edu.hmc.langtools._

class PicoParserTests extends FunSpec with LangParseMatchers[AST] {
  override val parser = PiconotParser.apply _


  describe("A state with one rule") {
    program(
      """
      inState foo
        surroundedBy NExx then move W newState bar
      """
    ) should parseAs (
      RulesList(
        List(
          InState(
            MyState("foo"),
            List(
              Single(
                SurroundedBy(News("NExx")),
                ThenMove(MoveDir('W')),
                NewState(MyState("bar"))
              )
            )
          )
        )
      )
    )

  }


  describe("A state with multiple rules") {
    program(
      """
      inState foo
        surroundedBy NExx then move W newState bar
        surroundedBy NxWx then move X newState bar
      """
    ) should parseAs (
      RulesList(
        List(
          InState(
            MyState("foo"),
            List(
              Single(
                SurroundedBy(News("NExx")),
                ThenMove(MoveDir('W')),
                NewState(MyState("bar"))
              ),
              Single(
                SurroundedBy(News("NxWx")),
                ThenMove(MoveDir('X')),
                NewState(MyState("bar"))
              )
            )
          )
        )
      )
    )

  }

  describe("Multiple states") {
    program(
      """
      inState foo
        surroundedBy NExx then move W newState bar
        surroundedBy NxWx then move X newState bar
      inState bar
        surroundedBy NxWx then move W newState bar
        surroundedBy Nxx* then move X newState foo
      """
    ) should parseAs (
      RulesList(
        List(
          InState(
            MyState("foo"),
            List(
              Single(
                SurroundedBy(News("NExx")),
                ThenMove(MoveDir('W')),
                NewState(MyState("bar"))
              ),
              Single(
                SurroundedBy(News("NxWx")),
                ThenMove(MoveDir('X')),
                NewState(MyState("bar"))
              )
            )
          ),
          InState(
            MyState("bar"),
            List(
              Single(
                SurroundedBy(News("NxWx")),
                ThenMove(MoveDir('W')),
                NewState(MyState("bar"))
              ),
              Single(
                SurroundedBy(News("Nxx*")),
                ThenMove(MoveDir('X')),
                NewState(MyState("foo"))
              )
            )
          )
        )
      )
    )
  }

}
