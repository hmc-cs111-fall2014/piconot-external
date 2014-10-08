
package GOTObot

import GOTObot.ir._

package object semantics {
  def eval(ast: AST): Int = ast match {
    case Rule(g, t, n) ⇒ 0
    case MultiRule(r, p) => 1
/*    case Plus(left, right) ⇒ eval(left) + eval(right)
    case Minus(left, right) ⇒ eval(left) - eval(right)
    case Mult(left, right) ⇒ eval(left) * eval(right)
    case Div(left, right) ⇒ eval(left) / eval(right)
    case Paren(enclosed) ⇒ eval(enclosed) */
  }
}
