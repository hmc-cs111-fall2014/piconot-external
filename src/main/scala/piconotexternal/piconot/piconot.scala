package piconotexternal
import scala.tools.nsc.EvalLoop

import piconotexternal.parser.PiconotParser
import piconotexternal.semantics.DSLSemantics._

import scala.io.Source._
import java.io.File

object Piconot extends EvalLoop with App {
  override def prompt = "><//> "

  loop { line =>
    val line_array = line.split(" +")
    val progfile = line_array(0)
    val mazefile = line_array(1)

    val progpath = "src" + File.separator + "main" + File.separator + "scala"  + File.separator +"piconotexternal" + File.separator + progfile

    val prog = fromFile(progpath, "utf-8").getLines.mkString

    PiconotParser(prog) match {
      case PiconotParser.Success(t, _) => eval(t, mazefile).run
      case e: PiconotParser.NoSuccess => println(e)
    }
  }

}
