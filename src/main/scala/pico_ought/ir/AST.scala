package pico_ought.ir

import picolib.semantics._


sealed abstract class AST
sealed abstract class Command extends AST
sealed abstract class Condition extends AST


// case class Section(name: String, commands: List[Command]) extends Command
case class Go(dir: Int, cond: Option[Map[Int, RelativeDescription]]) extends Command
case class Do(section: String) extends Command
case class Turn(dir: Int) extends Command
case class Face(dir: Int) extends Command

// case class If(cond: Condition, command: Command) extends Command


