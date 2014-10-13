package GOTObot

import scala.language.implicitConversions
import GOTObot.ir.GO._
import GOTObot.ir.TO._
// internal DSL for creating ASTs
package object ir {

  implicit def int2Number(i: Int): Num = Num(i)

  implicit class MultiRuleBuilder(val r: GOTORule) {
    def followedBy(p: Prog) = MultiRule(r, p)
  }
}
