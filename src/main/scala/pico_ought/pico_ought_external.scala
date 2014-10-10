package pico_ought

import scala.tools.nsc.EvalLoop
import pico_ought.parser.PicoOughtParser
import pico_ought.semantics.eval

object PicoOughtExternal extends EvalLoop with App {
  override def prompt = "> "

  loop { line ⇒
    PicoOughtParser(line) match {
      case PicoOughtParser.Success(t, _) ⇒ println(eval(t))
      case e: PicoOughtParser.NoSuccess  ⇒ println(e)
    }
  }
}
