// TODO: Parser goes here!
package piconotexternal.parser

import scala.util.parsing.combinator._
import piconotexternal.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
  def apply(s:String) :ParseResult[AST] = parseAll(listRules, s)

  lazy val listRules:PackratParser[Expr] =
    (
      rep(inState) ^^ {case rules => RulesList(rules)}
    )

  lazy val inState:PackratParser[InState] =
    (
      "inState"~state~rep(single) ^^ {case "inState"~state~singles => InState(state, singles)}
    )

  lazy val single:PackratParser[Single] =
    (
      surroundedBy~thenMove~newState ^^ {case news~dir~state => Single(news, dir, state)}
    )

  lazy val surroundedBy: PackratParser[SurroundedBy]=
    (
      "surroundedBy"~news ^^ {case "surroundedBy"~news => SurroundedBy(news)}
    )

  lazy val thenMove: PackratParser[ThenMove]=
    (
      "then move"~dir ^^ {case "then move"~dir => ThenMove(dir)}
    )

  lazy val newState: PackratParser[NewState]=
    (
      "newState"~state ^^ {case "newState"~newState => NewState(newState)}
    )

  lazy val news: PackratParser[News] =
    (
      """[\\*\w]{4}""".r ^^ {News}
    )

  lazy val dir: PackratParser[MoveDir] =
    (
      """[NEWSX]""".r ^^ {case x => MoveDir(x.head)}
    )

  lazy val state: PackratParser[MyState] =
    (
       """\w+""".r ^^ {MyState}
    )
}
