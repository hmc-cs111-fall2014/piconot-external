// TODO: Parser goes here!
package piconot.parser

import scala.util.parsing.combinator._
import piconot.ir._

object PiconotParser extends JavaTokenParsers with PackratParsers {
  // FIXME this might have to change from partial to rules
  def apply(s:String) :ParseResult[AST] = parseAll(partial, s)

  def rulesList: Parser[List[Single]] = rep(Single)
  def stateRules: Parser[List[InState]] = rep(InState)

  // the whole thing is a rep(inStateRules)
  // an inStateRule is a rep(SingleRules)

  lazy val single:PackratParser[Expr] =
    (
      //FIXME totally not done 
      surroundedBy~thenmove~newState ^^ {case _ => make rules}
    )

  /*
  lazy val partial: PackratParser[Expr] =
    (
      "surroundedBy"~news ^^ {case "surroundedBy"~news => SurroundedBy(news)}
      | "then move"~dir ^^ {case "then move"~dir => ThenMove(dir)}
      |"newState"~state ^^ {case "newState"~newState => NewState(newState)}
      | _ ^^ {THROW AN ERROR}
    )
  */

  lazy val surroundedBy: PackratParser[Expr]=
    (
      "surroundedBy"~news ^^ {case "surroundedBy"~news => SurroundedBy(news)}
    )

  lazy val thenMove: PackratParser[Expr]=
    (
      "then move"~dir ^^ {case "then move"~dir => ThenMove(dir)}
    )

  lazy val newState: PackratParser[Expr]=
    (
      "newState"~state ^^ {case "newState"~newState => NewState(newState)}
    )

  // FIXME: the syntax here might be completely wrong
  lazy val news: PackratParser[Expr] =
  (
    _ ^^ {case x => News(x)}
  )

  lazy val dir: PackratParser[Expr] =
  (
    _ ^^ {case x => MoveDir(x)}
  )

  lazy val state: PackratParser[Expr] =
  (
    _ ^^ {case x => MyState(x)}
  )
}

/*
 inState oldState + Rule*


 */
