package dsl.picobot


import dsl.picobot.parser.PicoParser
import dsl.picobot.semantics.eval
import scalafx.application.JFXApp

object bot extends JFXApp {
    if (parameters.raw.length != 1) {
      throw new IllegalArgumentException("Exactly one parameter expected")
    }
    
    val source = scala.io.Source.fromFile(parameters.raw(0))
    val contents = source.mkString
    source.close()
    
    val parsed = PicoParser(contents)
    val bot = eval(parsed.get)
    stage = bot.mainStage
}