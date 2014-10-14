package piconot
import java.io.File
import scala.io.Source
import piconot.parser.PiconotParser
import scala.tools.nsc.EvalLoop
import piconot.semantics.eval
import scalafx.application.JFXApp
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import scala.collection.mutable.MutableList

object Piconot extends App {
  //override def prompt = "> "
  val picobot_file = args(0)
  val maze_file = args(1)

  var rules: MutableList[Rule] = MutableList(); 
  PiconotParser(Source.fromFile(picobot_file).mkString) match {
      case PiconotParser.Success(t, _) ⇒ 
      {eval(t,rules, -1)}
      case e: PiconotParser.NoSuccess  ⇒ println(e)
	}
  print(rules.toList)
  val maze = Maze("resources" + File.separator + maze_file)
  val app = new Run(rules.toList, maze)
  app.main(Array())

		  /*
  loop { line ⇒
    CalcParser(line) match {
      case CalcParser.Success(t, _) ⇒ println(eval(t))
      case e: CalcParser.NoSuccess  ⇒ println(e)
    }
  }
  * */
}
class Run(val rules: List[Rule], val maze: Maze) extends JFXApp {
    object Bot extends Picobot(maze, rules)
         with TextDisplay with GUIDisplay

    stage = Bot.mainStage
}
