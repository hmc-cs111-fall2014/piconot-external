package piconot

import piconot.ir._

package object semantics {
  def eval(ast: AST): Unit = ast match {
    case Num(i) ⇒ print(i)
    case Program(left, right) ⇒ {eval(left); eval(right)}
    case State(number, ruleset) => {eval(number); eval(ruleset)}
    case RuleSet(left, right) => {eval(left); eval(right)}
    case Rule(w,d,n) => {eval(w); eval(d); eval(n)}
    case Walls(a,b,c,d) => {eval(a); eval(b); eval(c); eval(d)}
    case Wall(expr) => print(expr)
    case Direction(expr) => print(expr)
  }
}