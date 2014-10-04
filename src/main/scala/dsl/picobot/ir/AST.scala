package dsl.picobot.ir

sealed abstract class AST
sealed abstract class File extends AST

case class Equal(lhs: lhs, rhs: rhs) extends File
case class lhs(state: State, surrounding: Rest) extends File
case class rhs(state: State, nextDir: Dir) extends File

class Rest extends File
case class Plus(dir: Dir, rest: Rest) extends Rest
case class Minus(dir: Dir, rest: Rest) extends Rest
case class Mult(dir: Dir, rest: Rest) extends Rest

case class State(n: Int) extends File

class Surrounding extends File
case class Blocked() extends Surrounding
case class Open() extends Surrounding
case class Any() extends Surrounding

class Dir() extends File
case class N() extends Dir
case class E() extends Dir
case class W() extends Dir
case class S() extends Dir
case class Stay() extends Dir

