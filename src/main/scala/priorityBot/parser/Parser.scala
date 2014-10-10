package priorityBot.parser

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import priorityBot.ir._

object BotParser extends JavaTokenParsers with Parsers {
  
  // parsing interface
  def apply(s: String): ParseResult[AST] = parseAll(picobot, s)
  
  def picobot: Parser[Picobot] = 
    (    "maze = " ~ "\\w*\\.txt".r~rules ^^ 
    		{case "maze = "~mazeName~ruleList => Picobot(mazeName, ruleList)
    		case _ => Picobot("fail", Rules(List()))}
        )      
        
  def rules: Parser[Rules] = 
    (   rule~repN(3, opt(rule)) ^^
    		{case rule~moreRules => Rules(List(rule) ++ moreRules.toList.flatten) }
        )     
               
  def rule: Parser[Rule] = 
    (   cardinalDirection~"->"~repN(4, relativeDirection)^^
    		{case cardinal~"->"~dirs => Rule(cardinal, dirs(0), dirs(1), dirs(2), dirs(3))}
        )
  
  def cardinalDirection: Parser[CardinalDirection] = (
	    "[NEWS\\*]".r ^^ {case c => CardinalDirection(c)} 
     )
  
  def relativeDirection: Parser[RelativeDirection] = (
        "[FLBR]".r ^^ {case r => RelativeDirection(r)}
     )
  
}