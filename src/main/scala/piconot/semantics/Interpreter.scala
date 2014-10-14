package piconot

import piconot.ir._

package object semantics {
  def eval(ast: AST): Unit = ast match {
    case Num(i) ⇒ print(i)
    case Program(first, rest) ⇒ {eval(first); eval(rest)}
    case State(number, ruleset) => {eval(number); eval(ruleset)}
    case RuleSet(first, rest) => {eval(first); eval(rest)}
    // case Rule(w,d,n) => addRule(eval(w), eval(d), eval(n))
    case Rule(w,d,n) => {eval(w); eval(d); eval(n)}
    case Walls(n,e,w,s) => {eval(n); eval(e); eval(w); eval(s)}
    case Wall(expr) => print(expr)
    case Direction(expr) => print(expr)
  }
}