package picassem

import scala.tools.nsc.EvalLoop
import picassem.parser.PicParser
import picassem.semantics.eval

object PicAssembler extends EvalLoop with App {
  override def prompt = "> "
    
  loop { line =>
    PicParser(line) match {
      case PicParser.Success(t, _) => println(eval(t))
      case e: PicParser.NoSuccess  => println(e)
    }
  }
}