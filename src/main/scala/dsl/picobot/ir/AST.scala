package dsl.picobot.ir

sealed abstract class AST
sealed abstract class File extends AST

case class State(n: Int) extends File