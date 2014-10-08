package priorityBot.parser

import scala.util.parsing.combinator._
import priorityBot.ir

object BotParser extends JavaTokenParsers with PackratParsers {
  
  // parsing interface
  def apply(s: String): ParseResult[AST] = parseAll(program, s)
  
//  def program: PackratParser[Program] = 
//    (   "maze = "~>name~statement ^^ {case "maze = "~>mazeName => Maze(mazeName)}
//        )
//  def statement: PackratParser[Statement] = 
//    (   rule<~"\n"~>rule<~"\n"~>rule<~"\n"~>rule ^^ 
//    			{
//      | anyRule ^^
//        )
//  def rule: PackratParser[Rule] = 
//    (   rule<~"\n"~>rule<~"\n"~>rule<~"\n"~>rule ^^ 
//    			{case "maze = "~>mazeName => Maze(mazeName)}
//        )
  
  
}