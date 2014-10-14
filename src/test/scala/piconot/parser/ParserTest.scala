package piconot.parser

import org.scalatest._

import piconot.ir._
import piconot.parser._
import edu.hmc.langtools._

class MoveDirectionParserTests extends FunSpec with LangParseMatchers[MoveDirection] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.moveDirection, s)}

  describe("A valid move direction") {
    it("can be N") {
      program("N") should parseAs(North)
    } 

    it("can be E") {
      program("E") should parseAs(East)
    } 

    it("can be W") {
      program("W") should parseAs(West)
    } 

    it("can be S") {
      program("S") should parseAs(South)
    } 

    it("can be X") {
      program("X") should parseAs(StayPut)
    }
  }

  describe("An invalid move direction") {
    it("cannot be empty") {
      program("") should not (parse)
    } 

    it("cannot be multiple characters") {
      program("NEWS") should not (parse)
    } 

    it("cannot be an unknown character") {
      program("G") should not (parse)
    } 
  }
}

class SurroundingsParserTests extends FunSpec with LangParseMatchers[Surrounding] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.surrounding, s)}

  describe("A valid move direction") {
    it("can be N") {
      program("N") should parseAs(North)
    } 

    it("can be E") {
      program("E") should parseAs(East)
    } 

    it("can be W") {
      program("W") should parseAs(West)
    } 

    it("can be S") {
      program("S") should parseAs(South)
    } 
  }

  describe("An invalid move direction") {
    it("cannot be empty") {
      program("") should not (parse)
    }
    
    it("cannot be X") {
      program("X") should not (parse)
    }

    it("cannot be multiple characters") {
      program("NEWS") should not (parse)
    } 

    it("cannot be an unknown character") {
      program("G") should not (parse)
    } 
  }
}

class StateNumberParserTests extends FunSpec with LangParseMatchers[StateNumber] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.stateNumber, s)}

  describe("A valid stateNumber") {
    it("can be a single digit") {
      program("3") should parseAs(StateNumber(3))
    } 

    it("can be double-digit") {
      program("28") should parseAs(StateNumber(28))
    } 

    it("can be 0") {
      program("0") should parseAs(StateNumber(0))
    } 

    it("can be negative") {
      program("-3000") should parseAs(StateNumber(-3000))
    } 

    it("can have leading zeros") {
      program("0043") should parseAs(StateNumber(43))
    }
  }

  describe("An invalid state number") {
    it("cannot be empty") {
      program("") should not (parse)
    } 

    it("cannot be a decimal value") {
      program("3.27") should not (parse)
    } 

    it("cannot be a non-number") {
      program("G") should not (parse)
    } 
  }
}

class RuleParserTests extends FunSpec with LangParseMatchers[Rule] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.rule, s)}

  describe("A valid rule") {
    it("can be a normal-seeming rule") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(Rule(List(North, West), List(East, South), StayPut, StateNumber(4)))
    }
    
    it("can have no free directions") {
      program("FREE DIRECTIONS:" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(Rule(List(), List(East, South), StayPut, StateNumber(4)))
    } 

    it("can have no blocked directions") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS:" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(Rule(List(North, West), List(), StayPut, StateNumber(4)))
    }

    it("can have one free direction") {
      program("FREE DIRECTIONS: N" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(Rule(List(North), List(East, South), StayPut, StateNumber(4)))
    }

    it("can have one blocked direction") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(Rule(List(North, West), List(East), StayPut, StateNumber(4)))
    }     

    it("can have wonky spacing") {
      program("FREE DIRECTIONS:     N  ,  W" +
              "\nBLOCKED DIRECTIONS: E, S\t\t\t\t\n\n\t" +
              "\nMOVE DIRECTION:\n\t X" +
              "\nNEW STATE: 4\t\t   \n") should parseAs(Rule(List(North, West), List(East, South), StayPut, StateNumber(4)))
    }
  }
  
  describe("An invalid rule") {
    it("is not case sensitive") {
      program("Free DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should not (parse)
    }
    
    it("cannot omit colons") {
      program("FREE DIRECTIONS N, W" +
              "\nBLOCKED DIRECTIONS E, S" +
              "\nMOVE DIRECTION X" +
              "\nNEW STATE 4") should not (parse)
    }

    it("cannot omit move direction") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION:" +
              "\nNEW STATE: 4") should not (parse)
    }  

    it("cannot omit new state") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE:") should not (parse)
    }
    
    it("cannot have multiple move directions") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X, S" +
              "\nNEW STATE: 4") should not (parse)
    }
    
    it("cannot have multiple new states") {
      program("FREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4, 5") should not (parse)
    }    
    
    it("cannot omit spaces within keywords") {
      program("FREEDIRECTIONS: N, W" +
              "\nBLOCKEDDIRECTIONS: E, S" +
              "\nMOVEDIRECTION: X" +
              "\nNEWSTATE: 4") should not (parse)
    }   
  }
}

class StateParserTests extends FunSpec with LangParseMatchers[State] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.state, s)}

  describe("A valid state") {
    it("can contain one rule") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should parseAs(State(StateNumber(0), List(Rule(List(North, West), List(East, South), StayPut, StateNumber(4)))))
    }

    it("can contain multiple rules") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4" +
              "\n" +
              "\nFREE DIRECTIONS: N, E" +
              "\nBLOCKED DIRECTIONS: S, W" +
              "\nMOVE DIRECTION: S" +
              "\nNEW STATE: 3") should parseAs(State(StateNumber(0), List(Rule(List(North, West), List(East, South), StayPut, StateNumber(4)),
            		  													  Rule(List(North, East), List(South, West), South, StateNumber(3)))))
    }
  }
   
  describe("an invalid state") {
    it("cannot contain no rules") {
      program("STATE 0:") should not (parse)
    }
  }
}

class ProgramParserTests extends FunSpec with LangParseMatchers[AST] {
  override val parser = {(s:String) => PiconotParser.parseAll(PiconotParser.program, s)}

  describe("A valid program") {
    it("can contain one state") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4" +
              "\nRUN MAZE: mazeName.txt") should parseAs(Program(List(State(StateNumber(0), List(Rule(List(North, West), List(East, South), StayPut, StateNumber(4))))), "mazeName.txt"))
    }
    
    it("can contain multiple states") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4" +
              "STATE 1:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4" +
              "\nRUN MAZE: mazeName.txt") should parseAs(Program(List(State(StateNumber(0), List(Rule(List(North, West), List(East, South), StayPut, StateNumber(4)))),
                                                                      State(StateNumber(1), List(Rule(List(North, West), List(East, South), StayPut, StateNumber(4))))), "mazeName.txt"))
    }
  }
   
  describe("an invalid program") {
    it("cannot contain no states") {
      program("RUN MAZE: mazeName.txt") should not (parse)
    }

    it("cannot omit a maze name") {
      program("STATE 0:\nFREE DIRECTIONS: N, W" +
              "\nBLOCKED DIRECTIONS: E, S" +
              "\nMOVE DIRECTION: X" +
              "\nNEW STATE: 4") should not (parse)
    }
  }
}