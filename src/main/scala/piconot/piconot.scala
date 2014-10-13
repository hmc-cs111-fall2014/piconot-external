package piconot

import piconot.parser.PiconotParser
import scala.tools.nsc.EvalLoop
import piconot.semantics.eval


object Piconot extends App {
  //override def prompt = "> "
	PiconotParser("0 { **** -> N, 0 **** -> N,0}2 { **** -> N, 0}") match {
      case PiconotParser.Success(t, _) ⇒ eval(t)
      case e: PiconotParser.NoSuccess  ⇒ println(e)
	}
  //PiconotParser("0 { **** -> N, 0}");

		  /*
  loop { line ⇒
    CalcParser(line) match {
      case CalcParser.Success(t, _) ⇒ println(eval(t))
      case e: CalcParser.NoSuccess  ⇒ println(e)
    }
  }
  * */
  
}
