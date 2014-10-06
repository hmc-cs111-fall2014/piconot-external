sealed abstract class AST
sealed abstract class expr extends AST

case class News(stringDir:String) extends expr
case class MoveDir(charDir:Char) extends expr
case class MyState(stringState:String) extends expr

case class InState(oldState:String, cases:expr*) extends expr // ???
case class SurroundedBy(news:News) extends expr
case class ThenMove(newDir: MoveDir) extends expr
case class NewState(state: MyState) extends expr
