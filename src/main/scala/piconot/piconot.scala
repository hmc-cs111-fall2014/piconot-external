package piconot

/*
 * Modified by Hayden Blauzvern and Sarah Gilkinson
 */

import java.io.File

import picolib.maze.Maze
import picolib.semantics.{GUIDisplay, TextDisplay, Picobot}

import piconot.parser.PiconotParser
import piconot.semantics._

import scalafx.application.JFXApp

object Piconot extends JFXApp {

  val args = parameters.raw
  if (args.length < 2) {
    print("Too few arguments. Please include a bot file and a maze file.")
    System.exit(1)
  }
  val botFile = args(0)
  val mazeFile = args(1)
  val maze = Maze("resources" + File.separator + mazeFile)

  val program = scala.io.Source.fromFile(botFile)
  val lines = program.mkString
  program.close()

  PiconotParser(lines) match {
    case PiconotParser.Success(t, _) => println(eval(t))
    case e: PiconotParser.NoSuccess => println(e.msg)
  }

  object BotOfThePico extends Picobot(maze, listRules)
  with TextDisplay with GUIDisplay

  stage = BotOfThePico.mainStage
}
