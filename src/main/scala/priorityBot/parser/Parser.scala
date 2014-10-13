package priorityBot.parser

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import priorityBot.ir._

object BotParser extends JavaTokenParsers with Parsers {
  
  // parsing interface
  def apply(s: String): ParseResult[AST] = parseAll(picobot, s)
  
  def picobot: Parser[Priobot] = 
    (    "maze = " ~ "\\w*\\.txt".r~rules ^^ 
    		{case "maze = "~mazeName~ruleList => Priobot(mazeName, ruleList)
    		case _ => Priobot("fail", Rules(List()))}
    	| failure("Cannot parse maze. Should be of form: maze = maze.txt")
        )      
        
  def rules: Parser[Rules] = 
    (   rule~repN(3, opt(rule)) ^^
    		{case rule~moreRules => Rules(List(rule) ++ moreRules.toList.flatten) }
        )     
               
  def rule: Parser[PrioRule] = 
    (   cardinalDirection~"->"~repN(4, relativeDirection)^^
    		{case cardinal~"->"~dirs => PrioRule(cardinal, dirs(0), dirs(1), dirs(2), dirs(3))}
    	| failure("Cannot parse rule. Should be of form CardinalD -> RelativeD RelativeD RelativeD RelativeD")
        )
  
  def cardinalDirection: Parser[CardinalDirection] = (
	    "[NEWS\\*]".r ^^ {case c => CardinalDirection(c)}
	    | failure("Cannot parse the Cardinal Direction. Should be N, E, W, S, or *.")
     )
  
  def relativeDirection: Parser[RelativeDirection] = (
        "[FLBR]".r ^^ {case r => RelativeDirection(r)}
        | failure("Cannot parse the Relative Direction. Should be F, L, B, R")
     )
  
}