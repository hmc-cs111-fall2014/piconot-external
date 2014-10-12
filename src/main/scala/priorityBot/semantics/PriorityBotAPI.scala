package priorityBot.semantics

import java.io.File
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import scalafx.application.JFXApp
import scala.collection.mutable.MutableList

//object makeRules {
//  def apply(dir1 :List[Rule], dir2 :List[Rule], dir3 :List[Rule], dir4 :List[Rule]) : List[Rule] = {
//  		return List(dir1, dir2, dir3, dir4).flatten
//  	}
//}

// state we are in
abstract class CardinalState(val name: Char) {
	
	// make a mapping of this, and make the states the "MoveDirection". That way
	// we can generalize to just implement in cardinal state
	// map ((cardinalState, direction) => (MoveDirection))
	val moveDirMap = Map((('N', F) -> North), (('S', F) -> South), 
						 (('E', F) -> East),  (('W', F) -> West), 
						 (('N', B) -> South), (('S', B) -> North), 
						 (('E', B) -> West),  (('W', B) -> East), 
						 (('N', L) -> West),  (('S', L) -> East), 
						 (('E', L) -> North), (('W', L) -> South), 
						 (('N', R) -> East),  (('S', R) -> West), 
						 (('E', R) -> South), (('W', R) -> North))
	
	// method for determining the list of rules from the priority list
	def ->(directions: RelativeDirection*)	: List[Rule] = {
	    var listOfRules = new MutableList[Rule]
		var blockedDirs = new Surroundings(Anything, Anything, Anything, Anything)
	    
	    // Go through the priority list of directions
		directions.foreach(direction => {
		  val moveDirection = moveDirMap((name, direction))  
		  moveDirection match {
		    case North => {
		      listOfRules += new Rule(State(name.toString), 
		            Surroundings(	Open, 
		            			 	blockedDirs.east, 
		            			 	blockedDirs.west, 
		            			 	blockedDirs.south),
		            moveDirection,
		            State("N"))
		      blockedDirs = new Surroundings(	Blocked, 
									            blockedDirs.east, 
									            blockedDirs.west, 
									            blockedDirs.south	)
		    }
		    case South => {
		      listOfRules += new Rule(State(name.toString), 
		            Surroundings(	blockedDirs.north , 
		            				blockedDirs.east, 
		            				blockedDirs.west, 
		            				Open),
		            moveDirection,
		            State("S"))
		      blockedDirs = new Surroundings(	blockedDirs.north, 
									            blockedDirs.east, 
									            blockedDirs.west, 
									            Blocked	)
		    }
		    case East => {
		      listOfRules += new Rule(State(name.toString), 
		            Surroundings(	blockedDirs.north, 
		            				Open, 
		            				blockedDirs.west, 
		            				blockedDirs.south),
		            moveDirection,
		            State("E"))
		      blockedDirs = new Surroundings(	blockedDirs.north, 
									            Blocked, 
									            blockedDirs.west, 
									            blockedDirs.south	)
		    }
		    case West => {
		      listOfRules += new Rule(State(name.toString), 
		            Surroundings(	blockedDirs.north, 
		            				blockedDirs.east, 
		            				Open, 
		            				blockedDirs.south),
		            moveDirection,
		            State("W"))
		      blockedDirs = new Surroundings(	blockedDirs.north, 
									            blockedDirs.east, 
									            Blocked, 
									            blockedDirs.south	)
		    }
			
		  }}
		)
		
		return listOfRules.toList
	}		 
}


// directions the picobot is facing.
object N extends CardinalState('N')
object S extends CardinalState('S')
object E extends CardinalState('E')
object W extends CardinalState('W')
object AnyDir extends CardinalState('*')

// direction moving relative
abstract class RelativeDirection(val name: Char)

object F extends RelativeDirection('F')
object B extends RelativeDirection('B')
object L extends RelativeDirection('L')
object R extends RelativeDirection('R')



