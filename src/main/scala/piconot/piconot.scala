import scalafx.application.JFXApp
import piconot.parser._
import piconot.semantics._
import picolib.semantics.Picobot
import picolib.semantics.TextDisplay
import picolib.semantics.GUIDisplay
import java.io.File

object Piconot extends JFXApp {
  val fileName = parameters.raw(0)
  val program = scala.io.Source.fromFile("resources" + File.separator + fileName).mkString
  val parsedProgram = PiconotParser(program).get
  val picobot = eval(parsedProgram)
  object MazeBot extends Picobot(picobot.maze, picobot.rules)
        with TextDisplay with GUIDisplay
  stage = MazeBot.mainStage
}