package pico_ought

import pico_ought.ir._

import picolib.semantics._

import pico_ought.semantics.helpers._

package object semantics {
    def eval(ast: AST) = evalHelper(ast, 0, 0, List[String]())

    private def evalHelper(ast: AST,
             section_number: Int,
             line_number: Int,
             section_names: List[String]): List[Rule] = ast match {

        case Face(dir) => genFace(dir, section_number, line_number)

        case Turn(dir) => genTurn(dir, section_number, line_number)

        case Go(dir, conds) => genGo(dir, conds, section_number, line_number)
        // case Do(label)
    }
}