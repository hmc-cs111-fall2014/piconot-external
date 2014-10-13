package priorityBot.ir

/*
 * Grammar for this language:
 * 
 * Actual EBNF
 * 
 * picobot = mazeSpec, '\n', rules;
 * 
 * mazeSpec = 'maze = "', ?name of maze file, a txt file?, '"';
 * 
 * rules = EOF | rule, rules;
 * 
 * rule = cardinalDirection, whitespace, '->', whitespace, relativeDirections, '\n';
 * 
 * relativeDirections = relativeDirection, {whitepace, relativeDirection};
 * 
 * cardinalDirection = N | E | W | S | *;
 * 
 * relativeDirection = F | B | L | R;
 * 
 * opt whitespace = {' ', '\t'};
 * 
 * whitespace = [' ', '\t'], opt whitespace;
 * 
 */


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
