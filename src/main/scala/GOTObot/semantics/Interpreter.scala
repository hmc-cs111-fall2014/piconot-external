package GOTObot

import GOTObot.ir._
import picolib.semantics._
import GOTObot.ir.GO._
import GOTObot.ir.TO._

package object semantics {
  def eval(ast: AST): List[Rule] = ast match {
    case GOTORule(g, t, n) ⇒  addRules(g, t, n.n)
    case MultiRule(r, p) => eval(r) ::: eval(p)
  }

  object GlobalVars {
    var UserState = 0
    def state = UserState * 4
    def nState = state
    def wState = state + 1
    def sState = state + 2
    def eState = state + 3
  }

  def stateFromUserState(userState:Int, direction:Int) : State = {
    State((userState * 4 + direction).toString)
  }

  def fallThroughRule(initialState:Int) : Rule = {
    Rule(State(initialState.toString),
      Surroundings(Anything, Anything, Anything, Anything),
      StayHere,
      State((initialState + 4).toString))
  }

  def addRules(goType: GO, toType: TO, userState: Int) = {

    val specialSurrounding: RelativeDescription = goType match {
      case `o` => Open
      case `x` => Blocked
      case `*` => Anything
    }

    val movement = toType match {
      case F => MoveForward(userState)
      case X => StayStill(userState)
      case L => TurnLeft(userState)
    }

    val ruleN = Rule(State(GlobalVars.nState.toString),
      Surroundings(specialSurrounding, Anything, Anything, Anything),
      movement.move(GlobalVars.nState),
      movement.nextState(GlobalVars.nState))
    val ruleW = Rule(State(GlobalVars.wState.toString),
      Surroundings(Anything, Anything, specialSurrounding, Anything),
      movement.move(GlobalVars.wState),
      movement.nextState(GlobalVars.wState))
    val ruleS = Rule(State((GlobalVars.sState).toString),
      Surroundings(Anything, Anything, Anything, specialSurrounding),
      movement.move(GlobalVars.sState),
      movement.nextState(GlobalVars.sState))
    val ruleE = Rule(State((GlobalVars.eState).toString),
      Surroundings(Anything, specialSurrounding, Anything, Anything),
      movement.move(GlobalVars.eState),
      movement.nextState(GlobalVars.eState))
    val allRules = List(ruleN, ruleW, ruleS, ruleE,
      fallThroughRule(GlobalVars.nState),
      fallThroughRule(GlobalVars.wState),
      fallThroughRule(GlobalVars.sState),
      fallThroughRule(GlobalVars.eState))

    GlobalVars.UserState = GlobalVars.UserState + 1

    allRules
  }

  abstract class MovementType(val newState:Int) {
    def move(state:Int) : MoveDirection
    def nextState(oldState:Int) : State = {
      // Maintain directionality. Also each "state" corresponds to four states.
      State((newState + (oldState % 4)).toString)
    }
  }

  // Turn left
  class TurnLeft(state:Int) extends MovementType(state) {
    override def move(state:Int) : MoveDirection = {
      StayHere
    }
    override def nextState(oldState:Int) : State = {
      super.nextState(oldState + 1)
    }
  }

  object TurnLeft {
    def apply(userState:Int) = new TurnLeft(userState * 4)
  }

  // Move forward
  class MoveForward(state:Int) extends MovementType(state) {
    override def move(state:Int) : MoveDirection = {
      state % 4 match {
        case 0 => North
        case 1 => West
        case 2 => South
        case 3 => East
      }
    }
  }
  object MoveForward {
    def apply(userState:Int) = new MoveForward(userState * 4)
  }

  // Stand still
  class StayStill(state:Int) extends MovementType(state) {
    override def move(state:Int) : MoveDirection = {
      StayHere
    }
  }

  object StayStill {
    def apply(userState:Int) = new StayStill(userState * 4)
  }
}
