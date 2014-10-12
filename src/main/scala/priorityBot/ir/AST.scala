package priorityBot.ir

// PUT GRAMMAR HERE


sealed abstract class AST

case class Picobot(mazeName: String, rules: Rules) extends AST

case class Rules(rules: List[Rule]) extends AST

case class Rule(cardinal: CardinalDirection,
    dir1: RelativeDirection, 
    dir2: RelativeDirection,
    dir3: RelativeDirection,
    dir4: RelativeDirection)
    extends AST

case class CardinalDirection(direction: String) extends AST

case class RelativeDirection(direction: String) extends AST
