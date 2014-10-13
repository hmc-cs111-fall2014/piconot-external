package piconotexternal.parser

import scala.util.parsing.combinator._
import piconotexternal.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
  def apply(s:String) :ParseResult[AST] = parseAll(listRules, s)

  lazy val listRules:PackratParser[Expr] =
    (
      rep(inState) ^^ {case rules => RulesList(rules)}
      | failure("listrules")
    )

  lazy val inState:PackratParser[InState] =
    (
      "in state"~state~rep(single) ^^ {case "in state"~state~singles => InState(state, singles)}
      | failure("instate")
    )

  lazy val single:PackratParser[Single] =
    (
      surroundedBy~thenMove~newState ^^ {case news~dir~state => Single(news, dir, state)}
      | failure("single")
    )

  lazy val surroundedBy: PackratParser[SurroundedBy]=
    (
      "surrounded by"~news ^^ {case "surrounded by"~news => SurroundedBy(news)}
      | failure("surrounded by")
    )

  lazy val thenMove: PackratParser[ThenMove]=
    (
      "then move"~dir ^^ {case "then move"~dir => ThenMove(dir)}
      | failure("then move")
    )

  lazy val newState: PackratParser[NewState]=
    (
      "next state"~state ^^ {case "next state"~newState => NewState(newState)}
      | failure("next state")
    )

  lazy val news: PackratParser[News] =
    (
      """[\\*\w]{4}""".r ^^ {News}
      | failure("news")
    )

  lazy val dir: PackratParser[MoveDir] =
    (
      """[NEWSX]""".r ^^ {case x => MoveDir(x.head)}
      | failure("dir")
    )

  lazy val state: PackratParser[MyState] =
    (
       """\w+""".r ^^ {MyState}
      | failure("state")
    )
}
