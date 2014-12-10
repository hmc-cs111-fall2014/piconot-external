package piconot.semantics

import org.scalatest._
import scala.collection.mutable.MutableList

import piconot.parser.PiconotParser

import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings

/*
 * The same testing framework used for the ParserTest and the Calculator's SemanticsTest
 * didn't really make sense here, since we pass more than simply an AST into eval.
 * So, we are using a different framework and computing the outputs of text as a side
 * effect, passing a MutableList in to take the generated rules.
 */

class SemanticsTest extends FlatSpec with Matchers {

	"The program" should "produce rules from text" in {
	   var rules: MutableList[Rule] = MutableList();
	   PiconotParser("0{**** -> N, 0}") match {
	   	  case PiconotParser.Success(t, _) ⇒ 
	   		  {eval(t,rules, -1)}
	   	  case e: PiconotParser.NoSuccess  ⇒ println(e)
	   }
	   rules.toList should be (List(Rule(State("0"), Surroundings(Anything, Anything, Anything, Anything), North, State("0"))))
	}
	
	"Multiple states" should "generate the correct rules" in {
	  var rules: MutableList[Rule] = MutableList();
	  PiconotParser("0{**** -> N, 0  XXX_ -> S, 1} 1{**** -> E, 0}") match {
	   	  case PiconotParser.Success(t, _) ⇒ 
	   		  {eval(t,rules, -1)}
	   	  case e: PiconotParser.NoSuccess ⇒ println(e)
	  }
	  rules.toList should be(List(
	      Rule(State("0"), Surroundings(Anything, Anything, Anything, Anything), North, State("0")),
	      Rule(State("0"), Surroundings(Blocked, Blocked, Blocked, Open), South, State("1")),
	      Rule(State("1"), Surroundings(Anything, Anything, Anything, Anything), East, State("0"))))
	}
}