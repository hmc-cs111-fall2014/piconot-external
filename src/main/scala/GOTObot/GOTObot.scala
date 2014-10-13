package GOTObot

import GOTObot.parser.GOTOParser
import GOTObot.semantics.eval
import java.io.File
import picolib.maze.Maze
import picolib.semantics._
import scala.io.Source
import scalafx.application.JFXApp

object GOTORunner extends JFXApp {
	// Load in the user's file
	val source = Source.fromFile(parameters.raw(0))
	val code = source.mkString
	source.close()
	val parse = GOTOParser(code)
	val rules = eval(parse.get)

	// Load in the maze
	val mazeName = parameters.raw(1)
    val maze = Maze("resources" + File.separator + mazeName)

    // Run it
    object GOTOBot extends Picobot(maze, rules)
      with TextDisplay with GUIDisplay
    GOTOBot
}