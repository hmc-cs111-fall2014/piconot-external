package piconot

/*
 * Authors: Hayden Blauzvern and Sarah Gilkinson
 */

import java.io.File

import picolib.maze.Maze
import picolib.semantics.{GUIDisplay, TextDisplay, Picobot}
import piconot.parser.PiconotParser
import piconot.semantics._
import scalafx.application.JFXApp

/*
 * Instruction set to complete a maze using the right hand rule
 */
object Empty extends JFXApp {

  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = "If you are on Main St. and can go downtown, go downtown on Main St." +
    "If you are on Main St. and cannot go downtown and can go outta town, go outta town on Main St." +
    "If you are on Main St. and cannot go downtown and cannot go outta town, teleport to First Ave." +
    "If you are on First Ave. and can go uptown, go uptown on First Ave." +
    "If you are on First Ave. and cannot go uptown and can go into town, go into town on Second St." +
    "If you are on Second St. and can go downtown, go downtown on Second St." +
    "If you are on Second St. and cannot go downtown and can go into town, go into town on First Ave."

  PiconotParser(rules) match {
    case PiconotParser.Success(t, _) => println(eval(t))
    case e: PiconotParser.NoSuccess  => println(e)
  }

  object RightHandBot extends Picobot(emptyMaze, listRules)
  with TextDisplay with GUIDisplay

  stage = RightHandBot.mainStage
}
