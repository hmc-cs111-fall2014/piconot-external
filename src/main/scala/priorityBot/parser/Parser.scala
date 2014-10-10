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
     |  error("cannot parse list of rules")
        )     
        
// RepN option, should be idiomatic, but removing for debuging purposes
//  def rule: Parser[Rule] = 
//    (   cardinalDirection~" -> "~repN(4, relativeDirection) ^^
//    		{case cardinal~" -> "~dirs => Rule(cardinal, dirs(0), dirs(1), dirs(2), dirs(3))}
//     |  error("cannot parse rule")
//        )
//  
        
  def rule: Parser[Rule] = 
    (   cardinalDirection~" -> "~relativeDirection~relativeDirection~relativeDirection~relativeDirection ^^
    		{case cardinal~" -> "~dir1~dir2~dir3~dir4 => Rule(cardinal, dir1, dir2, dir3, dir4)}
     |  error("cannot parse rule")
     )
  
  def cardinalDirection: Parser[CardinalDirection] = (
	    "[NEWS\\*]".r ^^ {case c => CardinalDirection(c)} 
     |  error("cannot parse cardinal direction")
     )
  
  def relativeDirection: Parser[RelativeDirection] = (
        "[FLBR]".r ^^ {case r => RelativeDirection(r)}
     |  error("cannot parse relative direction")
     )
  
}