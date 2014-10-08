package piconot.ir

sealed abstract class AST
sealed abstract class Expr extends AST

case class RulesList(rules:List[InState]) extends Expr

case class News(stringDir:String) extends Expr
case class MoveDir(charDir:Char) extends Expr
case class MyState(stringState:String) extends Expr

case class InState(oldState:MyState, cases:List[Single]) extends Expr
case class Single(news:SurroundedBy, newDir: ThenMove, newState: NewState) extends Expr

case class SurroundedBy(news:News) extends Expr
case class ThenMove(newDir: MoveDir) extends Expr
case class NewState(state: MyState) extends Expr
