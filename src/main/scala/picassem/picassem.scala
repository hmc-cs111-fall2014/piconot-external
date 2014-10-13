package picassem

import scala.tools.nsc.EvalLoop
import picassem.parser.PicParser
import picassem.semantics.eval
import picassem.ir._
import java.io._

object PicAssembler extends EvalLoop with App {
  override def prompt = "> "
  var lines: scala.collection.mutable.MutableList[AST] = scala.collection.mutable.MutableList()
    
  // This loop now takes the filename as its argument
  loop {
    line => {
      // get the file as a list of commands
      try {
        
        val ruleList = io.Source.fromFile("input" + File.separator + line).getLines().toList
        // parse and run each line
        for (line <- ruleList) {
          PicParser(line) match {
	        case PicParser.Success(t, _) => eval(t)
	        case e: PicParser.NoSuccess  => println(e)
	      }
        }
	  } catch {
	    case e: Exception => {
	      println(e)
	    }
	  }
	}
  }
}