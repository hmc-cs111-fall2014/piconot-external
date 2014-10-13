package piconotexternal.parser

import scala.util.parsing.combinator._
import piconotexternal.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
  def apply(s:String) :ParseResult[AST] = parseAll(listRules, s)

  lazy val listRules:PackratParser[Expr] =
    (
      rep(inState) ^^ {case rules => RulesList(rules)}
      | failure("failed to parse a RulesList")
    )

  lazy val inState:PackratParser[InState] =
    (
      "in state"~state~rep(single) ^^ {case "in state"~state~singles => InState(state, singles)}
      | failure("failed to parse an inState")
    )

  lazy val single:PackratParser[Single] =
    (
      surroundedBy~thenMove~newState ^^ {case news~dir~state => Single(news, dir, state)}
      | failure("failed to parse a Single")
    )

  lazy val surroundedBy: PackratParser[SurroundedBy]=
    (
      "surrounded by"~news ^^ {case "surrounded by"~news => SurroundedBy(news)}
      | failure("failed to parse a SurroundedBy")
    )

  lazy val thenMove: PackratParser[ThenMove]=
    (
      "then move"~dir ^^ {case "then move"~dir => ThenMove(dir)}
      | failure("failed to parse a ThenMove")
    )

  lazy val newState: PackratParser[NewState]=
    (
      "next state"~state ^^ {case "next state"~newState => NewState(newState)}
      | failure("failed to parse a NewState")
    )

  lazy val news: PackratParser[News] =
    (
      """[\\*\w]{4}""".r ^^ {News}
      | failure("failed to parse a News")
    )

  lazy val dir: PackratParser[MoveDir] =
    (
      """[NEWSX]""".r ^^ {case x => MoveDir(x.head)}
      | failure("failed to parse a MoveDir")
    )

  lazy val state: PackratParser[MyState] =
    (
       """\w+""".r ^^ {MyState}
      | failure("failed to parse a MyState")
    )
}
