package priorityBot

import scala.io.Source
import priorityBot.semantics._
import priorityBot.parser._


object Main extends App {
	if (args.length < 1) {
	  println("You need to provide the file name as an argument! Exiting now...")
	  System.exit(1)
	}
	val filename = args(0)
	try {
	  val fileContents = Source.fromFile(filename).getLines.mkString
	  BotParser(fileContents) match {
	    case BotParser.Success(t, _) ⇒ eval(t)
	    case e: BotParser.NoSuccess  ⇒ println(e)
	  }
	} catch {
	  case ex: Exception => println(ex)
	}

}