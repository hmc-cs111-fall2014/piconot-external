package GOTObot

import scala.language.implicitConversions
import GOTObot.ir.GO._
import GOTObot.ir.TO._
// internal DSL for creating ASTs
package object ir {

  implicit def int2Number(i: Int): Num = Num(i)

  implicit class RuleBuilder(val g: GO) {
    def ->(t: TO)(n: Num) = Rule(g, t, n)
  }

  implicit class MultiRuleBuilder(val r: Rule) {
    def then(p: Prog) = MultiRule(r, p)
  }
}
