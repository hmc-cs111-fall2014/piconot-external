package pico_ought.semantics

import picolib.semantics._


package object helpers {
    
    // Face (up | right | down | left)
    def genFace(toFace: Int, 
                section_number: Int = 0, 
                line_number: Int = 0): List[Rule] = {
        List.range(1,5).map(dir => makeRule(
            section_number, line_number, dir,
            anySurroundings, StayHere,
            section_number, line_number + 1, toFace
            ))
    }

    def genTurn(turnAmount: Int, 
                section_number: Int = 0, 
                line_number: Int = 0): List[Rule] = {
        List.range(1,5).map(dir => makeRule(
            section_number, line_number, dir,
            anySurroundings, StayHere,
            section_number, line_number + 1, toDir(dir + turnAmount)
            ))
    }

    def genDo(label: String,
              section_names: List[String],
              section_number: Int = 0,
              line_number: Int = 0): List[Rule] = {

            val new_section = section_names.indexOf(label) + 1
            if (new_section == -1) println("Section " + label + " does not exist. :(")

            List.range(1,5).map( dir =>
                makeRule(section_number, line_number, dir,
                anySurroundings, StayHere,
                new_section, 1, dir) )
        }

    def genGo(dirDiff: Int,
              conds: Option[Map[Int, RelativeDescription]],
              section_number: Int = 0,
              line_number: Int = 0): List[Rule] = {
            
        conds match {
            case None => List.range(1,5).map( dir => 
                            makeRule(section_number, line_number, dir,
                                     anySurroundings, dirToMoveDirection(toDir(dir + dirDiff)),
                                     section_number, line_number + 1, dir))

            
            case Some(condsMap) => {
                val condsList = condsToList(condsMap)
                List.range(1,5).flatMap( dir =>
                    rulesFromDirAndSurr(section_number, line_number, dir, toDir(dir + dirDiff), condsList(dir-1)))
            }
        }
        
    }

    private def condsToList(conds: Map[Int, RelativeDescription]): List[Surroundings] = {
        val m = conds.withDefaultValue(Anything)
        List.range(0,4).map( dir =>  // Don't pay too much attention to these numbers -- So much magic
            Surroundings(m((4 - dir) %4), m((5 - dir) %4), m((7 - dir) %4), m((6 - dir) %4)) ) 

    }
                               

    private def rulesFromDirAndSurr(section_number: Int,
                                    line_number: Int,
                                    dirFacing: Int,
                                    moveDir: Int,
                                    surr: Surroundings): List[Rule] = {
            var someRules: List[Rule] = List(makeRule(section_number, line_number, dirFacing,
                                             surr, dirToMoveDirection(moveDir),
                                             section_number, line_number, dirFacing))
            if(surr.north != Anything) {
                someRules :+= makeRule(section_number, line_number, dirFacing,
                                       Surroundings(opposite(surr.north), Anything, Anything, Anything), StayHere,
                                       section_number, line_number + 1, dirFacing)
            }
            if(surr.east != Anything) {
                someRules :+= makeRule(section_number, line_number, dirFacing,
                                       Surroundings(Anything, opposite(surr.east), Anything, Anything), StayHere,
                                       section_number, line_number + 1, dirFacing)
            }
            if(surr.south != Anything) {
                someRules :+= makeRule(section_number, line_number, dirFacing,
                                       Surroundings(Anything, Anything, Anything, opposite(surr.south)), StayHere,
                                       section_number, line_number + 1, dirFacing)
            }
            if(surr.west != Anything) {
                someRules :+= makeRule(section_number, line_number, dirFacing,
                                       Surroundings(Anything, Anything, opposite(surr.west), Anything), StayHere,
                                       section_number, line_number + 1, dirFacing)
            }
            someRules
        }

    // Helper functions for internal use only
    private def opposite(relDesc: RelativeDescription): RelativeDescription = relDesc match {
        case Blocked => Open
        case Open => Blocked
        case Anything => Anything
        
    }
    
    private val dirToMoveDirection = Map( 1 -> North, 2 -> East, 3 -> South, 4 -> West )

    private def toDir(in: Int) = ((in - 1) % 4) + 1

    private val anySurroundings = Surroundings(Anything, Anything, Anything, Anything)

    private def makeRule(start_section: Int, start_line: Int, start_dir: Int,
                 surroundings: Surroundings, move_dir: MoveDirection,
                 end_section: Int, end_line: Int, end_dir: Int): Rule = {
        Rule(State(start_section + "0" + start_line + "0" + start_dir),
                    surroundings,
                    move_dir,
                    State(end_section + "0" + end_line + "0" + end_dir)
                    )
    }
}