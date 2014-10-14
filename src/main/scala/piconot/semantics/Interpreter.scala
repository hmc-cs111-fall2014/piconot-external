package piconot.semantics

// Import our internal representation
import piconot.ir._
import piconot._

// Import and rename picolib elements
// Basic Building Blocks
import picolib.maze.Maze
import java.io.File
import picolib.semantics.{State => PicoState}
import picolib.semantics.{Rule => PicoRule}
import picolib.semantics.{Surroundings => PicoSurroundings}

// Movement directions
import picolib.semantics.{MoveDirection => PicoMoveDirection}
import picolib.semantics.{North => PicoNorth}
import picolib.semantics.{East => PicoEast}
import picolib.semantics.{West => PicoWest}
import picolib.semantics.{South => PicoSouth}
import picolib.semantics.{StayHere => PicoHalt}

// Surrounding Components
import picolib.semantics.Picobot
import picolib.semantics.{RelativeDescription => PicoDescription}
import picolib.semantics.{Blocked => PicoBlocked}
import picolib.semantics.{Anything => PicoAnything};
import picolib.semantics.{Open => PicoOpen};

package object semantics {
  def eval(ast: AST): Picobot = ast match {
    case Program(filename, rules) ⇒ new Picobot(Maze("resources" + File.separator + filename), translateRulesList(rules.rulesList ))
    	
  }
  
  def translateRulesList(rules: List[Rule]): List[PicoRule] = 
    rules map (r => translateRule(r)) // let's get functional, ^functional^
    
  def translateRule(rule: Rule): PicoRule = rule match {
    case Rule(state1, surr, mov, state2) ⇒ 
    new PicoRule(PicoState(state1.n.toString),
        PicoSurroundings(translateSurrComp(surr.north),
        translateSurrComp(surr.east),
        translateSurrComp(surr.west),
        translateSurrComp(surr.south)),
        translateMovDir(mov.dir),
        PicoState(state2.n.toString))
  }
  
  // translation function for surrounding components
  def translateSurrComp(input: SurroundingComponentType): PicoDescription =  input match {
    case Blocked => PicoBlocked 
    case Wildcard => PicoAnything
    case Free => PicoOpen
  }
  
  // translation function for movement directions
  def translateMovDir(input: MoveDirectionType): PicoMoveDirection = input match {
    case MoveNorth => PicoNorth
    case MoveEast => PicoEast
    case MoveWest => PicoWest
    case MoveSouth => PicoSouth
    case Halt => PicoHalt
  }
}