package piconot

/*
 * Authors: Hayden Blauzvern and Sarah Gilkinson
 */

import java.io.File

import picolib.maze.Maze
import picolib.semantics.{GUIDisplay, TextDisplay, Picobot}

import scalafx.application.JFXApp

/*
 * Instruction set to complete a maze using the right hand rule
 */
object RightHand extends JFXApp {

  val rightHandMaze = Maze("resources" + File.separator + "maze.txt")

  "If you are on Main St. and can go outta_town then go outta_town on Second St."
  "If you are on Main St. and cannot go outta_town and can go uptown then go uptown on Main St."
  "If you are on Main St. and cannot go outta_town and cannot go uptown and can go into_town then go into_town on Third St."
  "If you are on Main St. and cannot go outta_town and cannot go uptown and cannot go into_town and can go downtown then go downtown on Fourth St."


  If. you. are. on("Second")("St."). and. can. go. downtown. then. go. downtown. on("Fourth")("St.")
  If. you. are. on("Second")("St."). and. cannot. go. downtown. and. can. go. outta_town. then. go. outta_town. on("Second")("St.")
  If. you. are. on("Second")("St."). and. cannot. go. downtown. and. cannot. go. outta_town. and. can. go. uptown. then. go. uptown. on("Main")("St.")
  If. you. are. on("Second")("St."). and. cannot. go. downtown. and. cannot. go. outta_town. and. cannot. go. uptown. and. can. go. into_town. then. go. into_town. on("Third")("St.")

  If. you. are. on("Third")("St."). and. can. go. uptown. then. go. uptown. on("Main")("St.")
  If. you. are. on("Third")("St."). and. cannot. go. uptown. and. can. go. into_town. then. go. into_town. on("Third")("St.")
  If. you. are. on("Third")("St."). and. cannot. go. uptown. and. cannot. go. into_town. and. can. go. downtown. then. go. downtown. on("Fourth")("St.")
  If. you. are. on("Third")("St."). and. cannot. go. uptown. and. cannot. go. into_town. and. cannot. go. downtown. and. can. go. outta_town. then. go. outta_town. on("Second")("St.")

  If. you. are. on("Fourth")("St."). and. can. go. into_town. then. go. into_town. on("Third")("St.")
  If. you. are. on("Fourth")("St."). and. cannot. go. into_town. and. can. go. downtown. then. go. downtown. on("Fourth")("St.")
  If. you. are. on("Fourth")("St."). and. cannot. go. into_town. and. cannot. go. downtown. and. can. go. outta_town. then. go. outta_town. on("Second")("St.")
  If. you. are. on("Fourth")("St."). and. cannot. go. into_town. and. cannot. go. downtown. and. cannot. go. outta_town. and. can. go. uptown. then. go. uptown. on("Main")("St.")

  object RightHandBot extends Picobot(rightHandMaze, rules)
  with TextDisplay with GUIDisplay

  stage = RightHandBot.mainStage
}
