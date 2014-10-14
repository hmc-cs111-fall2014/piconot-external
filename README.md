[Teams]: https://github.com/hmc-cs111-fall2014/piconot-external/wiki/Team-sign-ups
[API]: http://www.cs.hmc.edu/cs111/picolib/index.html#package
[ParserCombinatorResource]: http://bitwalker.org/blog/2013/08/10/learn-by-example-scala-parser-combinators/
[ParserCombinatorAPI]: http://www.scala-lang.org/files/archive/api/2.11.2/scala-parser-combinators/#package

# Piconot: The externaling
###### _Submission deadline: Monday, Oct. 13 at 11:59pm_
###### _Critique deadline: Wednesday, Oct. 15 at noon_

Now that you've had a chance to push internal DSLs to the limit, let's try
external DSLs!

Implement an external version of Piconot. You can choose to implement any
version of Piconot you want (e.g., your original version from the previous
assignment, another team's version, a new version, etc.). Just make sure it's a
sufficiently large language that you can get good practice with parser
combinators and with the language architecture and implementation process we
discussed in class.

## Checklist
 - [ ] [Sign up for teams][Teams]. Team 1 will have three people; all other 
    teams will have two people
 - [ ] Describe your design in `design.txt` 
 - [ ] Formally specify the syntax you plan to implement in `grammar-ideal.txt`.
 - [ ] Implement the syntax
     - [ ] Add files, as needed, to implement your syntax. Use the language
     architecture we discussed in class (more details below), and use parser
     combinators to implement your parser.
     - Include at least two example programs
       - [ ] `Empty.bot`
       - [ ] `RightHand.bot`
     - [ ] Provide instructions for how to run piconot on a file in `build.txt`
     - [ ] Describe your implementation process in `evaluation.txt`
     - [ ] Formally specify your final syntax in `grammar-final.txt`
  - [ ] Critique another team's design and implementation

## Design description and syntax specification

Describe the design of the language you'll implement, in the file `design.txt`
(see that file for instructions on what to write). If you're implementing the
exact same language you designed for the previous assignment, you can reuse the
contents of your file from that assignment (although it's probably a good idea
to revisit what you wrote to see if there's anything you would change).

Formally specify the syntax for your design, using EBNF. Place your definition
in the file `grammar-ideal.txt`. 

## Implement your syntax

Implement the language. Use [parser combinators][ParserCombinatorAPI] and case
classes to implement the parser. Here's a [good article][ParserCombinatorResource] 
for learning a bit more about parser
combinators; there are many other ones on the web. Chapter 19 of _Scala for the
Impatient_ also covers parser combinators. For more advanced languages, the
_Language Implementation Patterns_ book may also be useful.

A note about building the given code: use sbt! If you don't, you'll have to jump
through many difficult hoops to get your code to compile against the parser
combinator library.

When implementing your language, use the architecture from class:

```
main 
 |-- scala
      |-- package name
           |-- parser
           |-- ir
           |-- semantics

test 
 |-- scala
      |-- package name
           |-- parser
           |-- ir
           |-- semantics
```

### Errors

Your implementation should handle errors gracefully. Errors might include syntax
errors, programs that reference undefined rules, etc. **The team of three should
pay extra attention to error handling: the implementation should be extremely
robust, and the error messages should be clear and actionable.**

Giving good error messages for parsers is notoriously difficult. The `failure`, 
`positioned`, `phrase`, and `log` combinators (defined in the 
[scala.util.parsing.combinators.Parsers trait](http://www.scala-lang.org/files/archive/api/2.11.2/scala-parser-combinators/#scala.util.parsing.combinator.Parsers)) may be helpful.

## A running diary

As you work, comment on your experience in the file `evaluation.txt`. In
particular, if you change your ideal syntax, you should describe what
changed and why you made the change (e.g., your original idea was too hard to
implement or it didn't match well with the library calls) You should also answer
the following questions: On a scale of 1–10 (where 10 is "a lot"), how much did
you have to change your syntax? 

## Formalize your final syntax

After you've finished implementing, formally specify the syntax of your internal
DSL in `grammar-actual.txt`

## Provide build and run instructions

In the `build.txt` file provide instructions for how to build your software and
how to run it on piconot file. A somewhat easy way to do is the following:
```
sbt run "maze-file bot-file"
```
Note that for your users to run your language this way, you'll have to design
your solution so that the `main` function takes and processes an argument that
contains the filename of the maze file and the filename of the picobot program.

Alternatively, you can build a stand-alone jar file, which users can execute:
  1. build the stand-alone .jar file by running `sbt assembly` (note the location of the jar file that sbt generates)
  2. run the software on a file by executing the command 
```
scala -cp path-to-jar-file name-of-class-with-main-function maze-file bot-file
```

## Peer-review another team's work

Comment on another team's design and implementation. You should look through
their grammars, pay special attention to their `evaluation.txt` file, their
parser, and their intermediate representation. You might consider the following
questions:

  - What good insights about implementation did the team in `design.txt`? Did
  you have any experiences that were similar to the team?
  - How nice is their implementation? Is it understandable? Clean? Modular?
  Extensible? What do you like about their code? Are there any particularly
  elegant ways they overcame implementation challenges?
  - Download their code and run their example programs. How easy was it to run? 
  Did the instructions work?
  - Modify the example programs to introduce errors. How robust is their
  implementation? What do you like about their error-handling? What can be improved?
  - Are there any implementation tricks that you can suggest to the team?
  Anything you see that might make the implementation easier or more like the
  original design?
