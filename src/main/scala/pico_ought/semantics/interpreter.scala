package pico_ought

import pico_ought.ir._

import picolib.semantics._

import pico_ought.semantics.helpers._

package object semantics {
    def eval(ast: AST): List[Rule] = ast match {
        case Program(sections) => 
            val section_names = sections.map(_.name)
            sections.flatMap{ s => evalSection(s, section_names) }

        // To allow us to test individual commands
        case c: Command => evalCommand(c, 0, 0, List[String]()) 

        case _ => 
            println("We made a bad parser")
            List()
    }

    private def evalSection(section: Section, 
                            section_names: List[String]): List[Rule] = {
        val section_number = section_names.indexOf(section.name) + 1 // This makes Tyler sad.
        section.commands.zipWithIndex.flatMap {
            case (command, line_number) => 
                evalCommand(command, section_number, line_number + 1, section_names)
        }
    }

    private def evalCommand(command: Command,
             section_number: Int,
             line_number: Int,
             section_names: List[String]): List[Rule] = command match {

        case Face(dir) => genFace(dir, section_number, line_number)

        case Turn(dir) => genTurn(dir, section_number, line_number)

        case Go(dir, conds) => genGo(dir, conds, section_number, line_number)
        
        case Do(label) => genDo(label, section_names, section_number, line_number)

        case If(conds) => genIf(conds, section_number, line_number)
    }
}