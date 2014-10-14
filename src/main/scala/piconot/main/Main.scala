package piconot.main

// Grab our implementation of parser and interpreter
import piconot.ir._
import piconot.semantics.semantics._
import piconot.parser._

// Grab picolib components
import picolib.semantics.Picobot
import picolib.semantics.GUIDisplay
import picolib.semantics.TextDisplay

// Grab graphics and Java libraries
import scalafx.application.JFXApp
import java.io.File

object Main extends JFXApp{
  // Pull down parser and interpreter
  val parser = PiconotParser.apply _
  val interpreter = eval _ 
    
  // Open file, parse + interpret the results
  val filename = parameters.raw(0)
  val file_as_string = scala.io.Source.fromFile(filename).mkString
  val parsed_file = parser(file_as_string).get
  val picobot = interpreter(parsed_file)
  
  val maze = picobot.maze
  val rules = picobot.rules
  
  object Bot extends Picobot(maze,rules) with TextDisplay with GUIDisplay
  
  stage = Bot.mainStage
}