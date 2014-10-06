package dsl.picobot.ir

sealed abstract class AST
sealed abstract class Program extends AST

case class Equal(lhs: Program, rhs: Program) extends Program
case class Lhs(state: State, surrounding: Option[Rest]) extends Program
case class Rhs(state: State, nextDir: Dir) extends Program

class Rest extends Program
case class Plus(dir: Dir, rest: Option[Rest]) extends Rest
case class Minus(dir: Dir, rest: Option[Rest]) extends Rest
case class Mult(dir: Dir, rest: Option[Rest]) extends Rest




case class State(n: Int) extends Program

class Dir() extends Program
case class N() extends Dir
case class E() extends Dir
case class W() extends Dir
case class S() extends Dir
case class Stay() extends Dir

