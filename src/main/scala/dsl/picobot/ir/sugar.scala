//package dsl.picobot.ir
//
//import scala.language.implicitConversions
//
//package object ir {
//  //for using a state on its own  
//  implicit def int2State(i: Int): State = State(i)
//  
//  //stolen from calculator.sugar
////  // to use a number as part of a binary operation...
////  implicit def intToExprBuilder(n: Int) = new ExprBuilder(Num(n))
////
////  // to build up operations using infix notation from left to right...
////  // ExprBuilder saves the left operand and defines methods that 
////  //   take the right operand and returns the appropriate Expr 
////  implicit class ExprBuilder(val left: Expr) {
////    def |+|(right: Expr) = Plus(left, right)
////    def |-|(right: Expr) = Minus(left, right)
////    def |*|(right: Expr) = Multiply(left, right)
////  }
////  
//
//}
//}