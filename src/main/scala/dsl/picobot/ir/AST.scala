package dsl.picobot.ir

sealed abstract class AST
sealed abstract class Program extends AST

sealed abstract class Rule extends Program


case class Equal(lhs: Rule, rhs: Rule) extends Rule
case class Lhs(state: State, surrounding: List[Rule]) extends Rule
case class Rhs(state: State, nextDir: Dir) extends Rule

sealed abstract class Surrounding extends Rule
// TODO: rename to blocked, open, any
case class Plus(dir: Dir) extends Surrounding
case class Minus(dir: Dir) extends Surrounding
case class Mult(dir: Dir) extends Surrounding




case class State(n: Int) extends Rule

class Dir() extends Rule
case class N() extends Dir
case class E() extends Dir
case class W() extends Dir
case class S() extends Dir
case class Stay() extends Dir

