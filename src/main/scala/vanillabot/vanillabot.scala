package vanillabot

import picolib.maze.Maze
import picolib.semantics.Picobot
import vanillabot.syntax.VanillaBotParser
import java.io.FileNotFoundException
import picolib.semantics.TextDisplay
import scalafx.application.JFXApp
import picolib.semantics.GUIDisplay
import semantics.checks._

object VanillaBot extends JFXApp {

    val args = parameters.raw
    
    // Error handling: did the user pass two arguments?
    if (args.length != 2) {
      println(usage)
      sys.exit(1)
    }

    // parse the maze file
    val mazeFileName = args(0)
    val maze = Maze(getFileLines(mazeFileName))
    
    // parse the program file
    val programFilename = args(1)
    val program = VanillaBotParser(getFileContents(programFilename))

    // process the results of parsing
    program match {
      
      // Error handling: syntax errors
      case e: VanillaBotParser.NoSuccess  ⇒ println(e)
      
      // if parsing succeeded...
      case VanillaBotParser.Success(t, _) ⇒ {
        val bot = new Picobot(maze, program.get) with TextDisplay with GUIDisplay
        checkErrors(bot)        
      }
    }

  /** A string that describes how to use the program **/
  def usage = "usage: vanillabot.VanillaBot <maze-file> <rules-file>"

  /**
   * Given a filename, get a list of the lines in the file
   */
  def getFileLines(filename: String): List[String] =
    try {
      io.Source.fromFile(filename).getLines().toList
    }
    catch { // Error handling: non-existent file
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }

  /**
   * Given a filename, get the contents of the file
   */
  def getFileContents(filename: String): String =
    try {
      io.Source.fromFile(filename).mkString
    }
    catch { // Error handling: non-existent file
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }

  /**
   * Check for errors. If there are any print them and exit
   */
  def checkErrors(bot: Picobot): Unit = {
    val checker = new ErrorCollector[Picobot]() 
        with MoveToWall with BoxedIn with UndefinedStates with UreachableStates
    checker.check(bot)
    if (!checker.errors.isEmpty) {
      checker.errors foreach println
      sys.exit(1)
    }
  }
}