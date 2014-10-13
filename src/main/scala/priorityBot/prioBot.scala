package priorityBot

import scala.io.Source
import priorityBot.semantics._
import priorityBot.parser._
import scalafx.application.JFXApp
import picolib.semantics._


object prioBot extends JFXApp {
	if (parameters.raw.length < 1) {
	  println("You need to provide the file name as an argument! Exiting now...")
	  System.exit(1)
	}
	val filename = parameters.raw(0)
	try {
	  val fileContents = Source.fromFile(filename).getLines.mkString
	  BotParser(fileContents) match {
	    case BotParser.Success(t, _) ⇒ {
		    val bot = eval(t)
		    stage = bot.mainStage;
	    }
	    case e: BotParser.NoSuccess  ⇒ println(e)
	  }
	} catch {
	  case ex: Exception => println(ex)
	}
}