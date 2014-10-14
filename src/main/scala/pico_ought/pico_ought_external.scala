package pico_ought

import java.io.File
import scala.io.Source

import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.TextDisplay
import picolib.semantics.GUIDisplay
import pico_ought.parser.PicoOughtParser
import pico_ought.semantics.eval
import picolib.maze.Maze
import scalafx.application.JFXApp

object PicoOughtExternal extends App{

        val picobot_file = args(0)
        val maze_file = args(1)

        val parse_rules = PicoOughtParser(Source.fromFile(picobot_file).mkString)

        if(parse_rules.successful) {
            val rules = eval(parse_rules.get)

            println("PicoOught generated " + rules.length + " rules for your enjoyment.")

            val maze = Maze("resources" + File.separator + maze_file)
            println("Loaded maze: " + maze_file)

            val app = new RunApp(rules, maze)
            app.main(Array())
        } else {
            println("[" + Console.RED + "error" + Console.RESET + "]" + 
                " Failed to parse: " + Console.BLUE + parse_rules + Console.RESET)
            System.exit(1)
        }

}

// Helper class for main to launch the JFXApp app
class RunApp(val rules: List[Rule], val maze: Maze) extends JFXApp {
    object Bot extends Picobot(maze, rules)
         with TextDisplay with GUIDisplay

    stage = Bot.mainStage
}