package dsl.picobot


//import scala.tools.nsc.EvalLoop
import dsl.picobot.parser.PicoParser
import dsl.picobot.semantics.eval
import scalafx.application.JFXApp

//object bot extends EvalLoop with App {
object bot extends JFXApp {
  override def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      throw new IllegalArgumentException("Exactly one parameter expected")
    }
    val source = scala.io.Source.fromFile(args(0))
    val contents = source.mkString
    source.close()
    
    val parsed = PicoParser(contents)
    val bot = eval(parsed.get)
    stage = bot.mainStage	
  }

//  loop { line =>
//    PicoParser(line) match {
//      case PicoParser.Success(t, _) => println(eval(t))
//      case e: PicoParser.NoSuccess => println(e)
//    }
//  }
}