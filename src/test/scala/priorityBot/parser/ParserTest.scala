package priorityBot.parser

import org.scalatest._

import priorityBot.ir._
import priorityBot.parser._
import edu.hmc.langtools._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  
  override val parser = BotParser.apply _

}