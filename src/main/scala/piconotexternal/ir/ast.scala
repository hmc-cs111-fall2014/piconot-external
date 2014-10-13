package piconotexternal.ir

sealed abstract class AST
sealed abstract class Expr extends AST

case class RulesList(rules:List[InState]) extends Expr

case class News(val stringDir:String) extends Expr
case class MoveDir(val charDir:Char) extends Expr
case class MyState(val stringState:String) extends Expr

case class InState(oldState:MyState, cases:List[Single]) extends Expr
case class Single(val news:SurroundedBy, val newDir: ThenMove, val newState: NewState) extends Expr

case class SurroundedBy(val news:News) extends Expr
case class ThenMove(val newDir: MoveDir) extends Expr
case class NewState(val state: MyState) extends Expr
