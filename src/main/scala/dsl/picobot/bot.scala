package dsl.picobot

import scala.tools.nsc.EvalLoop
import dsl.picobot.parser.PicoParser
import dsl.picobot.semantics.eval

object bot extends EvalLoop with App {
  override def prompt = "> "

  loop { line =>
    PicoParser(line) match {
      case PicoParser.Success(t, _) => println(eval(t))
      case e: PicoParser.NoSuccess => println(e)
    }
  }
}