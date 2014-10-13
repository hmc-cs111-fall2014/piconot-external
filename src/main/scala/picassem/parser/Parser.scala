package picassem.parser

import scala.util.parsing.combinator._
import picassem.ir._

object PicParser extends JavaTokenParsers with PackratParsers {
	// parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(expr, s)
    
    // expressions
    lazy val expr: PackratParser[Expr] = {
      (  "JMPNE" ^^ {case "JMPNE" => JumpNext()}
	     | "JMP" ^^ {case "JMP" => Jump()}
	     | "MOV"~reg~","~bin ^^ {case "MOV"~l~","~r => Move(l, r)}
	     | "BEGIN"~reg ^^ {case "BEGIN"~l => Begin(l)}
	     | "END" ^^ {case "END" => End()}
	  )
    }
    
    lazy val reg: PackratParser[String] = {
      Register
    }
    
    lazy val bin: PackratParser[Int] = {
      Binary
    }
    
    def Register: Parser[String] = stringLiteral ^^ {s => s}
    
    def Binary: Parser[Int] = stringLiteral ^^ {
	    s => { binConvert(s) }
    }
    
    def binConvert(s: String): Int = {
      val len:Int = s.length();
      var result:Int = 0
      var curr:Int = 0
      for (i <- 1 to len-2) {
        curr = s.substring(i, i+1).toInt
        result += curr*(math.pow(2, len-2-i).toInt)
      }
      result
    } 
    
    
}