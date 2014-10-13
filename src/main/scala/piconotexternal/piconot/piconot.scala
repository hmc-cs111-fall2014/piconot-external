package piconotexternal
import scala.tools.nsc.EvalLoop

import piconotexternal.parser.PiconotParser
import piconotexternal.semantics.DSLSemantics._

import scala.io.Source._

object Piconot extends EvalLoop with App {
  override def prompt = "><//>"

  loop { line =>
    val line_array = line.split(" +")
    val progfile = line_array(0)
    val mazefile = line_array(1)

    val prog = fromFile(progfile, "utf-8").getLines.mkString
    eval(PiconotParser(prog), mazefile);
    //println((line.split(" +")).deep.mkString("\n"));
  }

  // This is main!

}
