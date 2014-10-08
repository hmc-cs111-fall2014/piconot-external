package calculator

import scala.tools.nsc.EvalLoop
import calculator.parser.CalcParser
import calculator.semantics.eval

object Calculator extends EvalLoop with App {
  override def prompt = "> "

  loop { line ⇒
    CalcParser(line) match {
      case CalcParser.Success(t, _) ⇒ println(eval(t))
      case e: CalcParser.NoSuccess  ⇒ println(e)
    }
  }
}
