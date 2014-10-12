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
object RightHand extends JFXApp {

  val rightHandMaze = Maze("resources" + File.separator + "maze.txt")

  val rules = "If you are on Main St. and can go outta town, go outta town on Second St." +
    "If you are on Main St. and cannot go outta town and can go uptown, go uptown on Main St." +
    "If you are on Main St. and cannot go outta town and cannot go uptown and can go into town, go into town on Third St." +
    "If you are on Main St. and cannot go outta town and cannot go uptown and cannot go into town and can go downtown, go downtown on Fourth St." +
    "If you are on Second St. and can go downtown, go downtown on Fourth St." +
    "If you are on Second St. and cannot go downtown and can go outta town, go outta town on Second St." +
    "If you are on Second St. and cannot go downtown and cannot go outta town and can go uptown, go uptown on Main St." +
    "If you are on Second St. and cannot go downtown and cannot go outta town and cannot go uptown and can go into town, go into town on Third St." +
    "If you are on Third St. and can go uptown, go uptown on Main St." +
    "If you are on Third St. and cannot go uptown and can go into town, go into town on Third St." +
    "If you are on Third St. and cannot go uptown and cannot go into town and can go downtown, go downtown on Fourth St." +
    "If you are on Third St. and cannot go uptown and cannot go into town and cannot go downtown and can go outta town, go outta town on Second St." +
    "If you are on Fourth St. and can go into town, go into town on Third St." +
    "If you are on Fourth St. and cannot go into town and can go downtown, go downtown on Fourth St." +
    "If you are on Fourth St. and cannot go into town and cannot go downtown and can go outta town, go outta town on Second St." +
    "If you are on Fourth St. and cannot go into town and cannot go downtown and cannot go outta town and can go uptown, go uptown on Main St."

  PiconotParser(rules) match {
    case PiconotParser.Success(t, _) => println(eval(t))
    case e: PiconotParser.NoSuccess  => println(e)
  }

  object RightHandBot extends Picobot(rightHandMaze, listRules)
  with TextDisplay with GUIDisplay

  stage = RightHandBot.mainStage
}
