package GOTObot

import GOTObot.parser.GOTOParser
import GOTObot.parser.GOTOParser.NoSuccess
import GOTObot.semantics.eval
import java.io.File
import picolib.maze.Maze
import picolib.semantics._
import scala.io.Source
import scalafx.application.JFXApp

object GOTORunner extends JFXApp {
	class GOTOParseException(str: String) extends Exception(str)

	// Load in the user's file
	val source = Source.fromFile(parameters.raw(0))
	val code = source.mkString
	source.close()
   	val parse = GOTOParser(code)
   	parse match {
   		case GOTOParser.NoSuccess(msg, next) => throw new GOTOParseException(msg)
   		case GOTOParser.Success(parsedRules, _) => {
   			val rules = eval(parsedRules)

			// Load in the maze
			val mazeName = parameters.raw(1)
		   	val maze = Maze("resources" + File.separator + mazeName)

		    // Run it
		   	object GOTOBot extends Picobot(maze, rules)
		   	with TextDisplay with GUIDisplay
		    	
		   	GOTOBot
   		}

   	}
}