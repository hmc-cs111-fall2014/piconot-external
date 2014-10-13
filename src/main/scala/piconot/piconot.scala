package piconot

/*
 * Modified by Sarah Gilkinson
 */

import picolib.semantics.{GUIDisplay, TextDisplay, Picobot}
import piconot.Empty._

import scala.tools.nsc.EvalLoop
import piconot.parser.PiconotParser
import piconot.semantics._

import scalafx.application.JFXApp

object Piconot extends EvalLoop with JFXApp {
  override def prompt = "> "

  loop { filename =>
    val program = scala.io.Source.fromFile(filename)
    val lines = program.mkString
    program.close()
    PiconotParser(lines) match {
      case PiconotParser.Success(t, _) => println(eval(t))
      case e: PiconotParser.NoSuccess => println(e)
    }
    object RightHandBot extends Picobot(emptyMaze, listRules)
    with TextDisplay with GUIDisplay

    stage = RightHandBot.mainStage
  }
}
