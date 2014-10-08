package dsl.picobot.ir

sealed abstract class AST
case class Program(declaration: Declaration, consider: Consider) extends AST

case class Declaration(mazename: String) extends AST
case class Consider(rules: List[Rule]) extends AST
// TODO?: implement QED here to make it super cool

case class Rule(lhs: Lhs, rhs: Rhs) extends AST
case class Lhs(state: State, surrounding: List[Surrounding]) extends AST
case class Rhs(state: State, nextDir: Dir) extends AST

sealed abstract class Surrounding extends AST
// TODO: rename to blocked, open, any
case class Plus(dir: Dir) extends Surrounding
case class Minus(dir: Dir) extends Surrounding
case class Mult(dir: Dir) extends Surrounding

case class State(n: Int) extends AST

sealed abstract class Dir() extends AST
case class N() extends Dir
case class E() extends Dir
case class W() extends Dir
case class S() extends Dir
case class Stay() extends Dir


