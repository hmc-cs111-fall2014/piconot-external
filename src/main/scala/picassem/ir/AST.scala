package picassem.ir

sealed abstract class AST
sealed abstract class Expr extends AST

case class Jump() extends Expr
case class JumpNext() extends Expr
case class Move(reg: String, bin: Int) extends Expr
case class Comp(reg: String, bin: Int) extends Expr
case class And(reg1: String, reg2: String, bin:Int) extends Expr