package priorityBot.ir

// PUT GRAMMAR HERE


sealed abstract class AST

case class Priobot(mazeName: String, rules: Rules) extends AST

case class Rules(rules: List[PrioRule]) extends AST

case class PrioRule(cardinal: CardinalDirection,
    dir1: RelativeDirection, 
    dir2: RelativeDirection,
    dir3: RelativeDirection,
    dir4: RelativeDirection)
    extends AST

case class CardinalDirection(direction: String) extends AST

case class RelativeDirection(direction: String) extends AST
