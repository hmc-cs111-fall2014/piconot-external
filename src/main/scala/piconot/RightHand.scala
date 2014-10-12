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

  val rules = "If you are on Main St. and you can go outta_town go outta_town on Second St." +
    "If you are on Main St. and you cannot go outta_town and you can go uptown go uptown on Main St." +
    "If you are on Main St. and you cannot go outta_town and you cannot go uptown and you can go into_town go into_town on Third St." +
    "If you are on Main St. and you cannot go outta_town and you cannot go uptown and you cannot go into_town and you can go downtown go downtown on Fourth St." +
    "If you are on Second St. and you can go downtown go downtown on Fourth St." +
    "If you are on Second St. and you cannot go downtown and you can go outta_town go outta_town on Second St." +
    "If you are on Second St. and you cannot go downtown and you cannot go outta_town and you can go uptown go uptown on Main St." +
    "If you are on Second St. and you cannot go downtown and you cannot go outta_town and you cannot go uptown and you can go into_town go into_town on Third St." +
    "If you are on Third St. and you can go uptown go uptown on Main St." +
    "If you are on Third St. and you cannot go uptown and you can go into_town go into_town on Third St." +
    "If you are on Third St. and you cannot go uptown and you cannot go into_town and you can go downtown go downtown on Fourth St." +
    "If you are on Third St. and you cannot go uptown and you cannot go into_town and you cannot go downtown and you can go outta_town go outta_town on Second St." +
    "If you are on Fourth St. and you can go into_town go into_town on Third St." +
    "If you are on Fourth St. and you cannot go into_town and you can go downtown go downtown on Fourth St." +
    "If you are on Fourth St. and you cannot go into_town and you cannot go downtown and you can go outta_town go outta_town on Second St." +
    "If you are on Fourth St. and you cannot go into_town and you cannot go downtown and you cannot go outta_town and you can go uptown go uptown on Main St."

  PiconotParser(rules) match {
    case PiconotParser.Success(t, _) => println(eval(t))
    case e: PiconotParser.NoSuccess  => println(e)
  }

  object RightHandBot extends Picobot(rightHandMaze, listRules)
  with TextDisplay with GUIDisplay

  stage = RightHandBot.mainStage
}
