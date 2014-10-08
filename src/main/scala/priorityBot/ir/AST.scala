package priorityBot.ir

// PUT GRAMMAR HERE


sealed abstract class AST
sealed abstract class Program extends AST
sealed abstract class Statement extends AST
sealed abstract class CardinalDirection extends Statement
sealed abstract class RelativeDirection extends Statement

//case class CardinalDirection(direction: )

case class Rule(cardinal: CardinalDirection,
    dir1: RelativeDirection, 
    dir2: RelativeDirection,
    dir3: RelativeDirection,
    dir4: RelativeDirection)
    extends Statement
    
case class Maze(mazeName: String) extends Statement

//
//case class F extends RelativeDirection
//case class B extends RelativeDirection
//case class L extends RelativeDirection
//case class R extends RelativeDirection
//case class Front extends RelativeDirection
//case class Back extends RelativeDirection
//case class Left extends RelativeDirection
//case class Right extends RelativeDirection