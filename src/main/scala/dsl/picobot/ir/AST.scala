package dsl.picobot.ir

sealed abstract class AST
sealed abstract class Program extends AST

case class Equal(lhs: lhs, rhs: rhs) extends Program
case class lhs(state: State, surrounding: Rest) extends Program
case class rhs(state: State, nextDir: Dir) extends Program

class Rest extends Program
case class Plus(dir: Dir, rest: Option[Rest]) extends Rest
case class Minus(dir: Dir, rest: Option[Rest]) extends Rest
case class Mult(dir: Dir, rest: Option[Rest]) extends Rest

case class Action(state: State, dir: Dir) extends Program



case class State(n: Int) extends Program

class Dir() extends Program
case class N() extends Dir
case class E() extends Dir
case class W() extends Dir
case class S() extends Dir
case class Stay() extends Dir

