package pico_ought.ir

sealed abstract class AST
sealed abstract class Command extends AST


// case class Section(name: String, commands: List[Command]) extends Command
// case class Go(dir: Int, cond: Condition) extends Command
// case class Do(section: Section) extends Command
// case class Turn(dir: Int) extends Command
case class Face(dir: Int) extends Command

// case class If(cond: Condition, command: Command) extends Command


