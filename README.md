[Parser]: https://github.com/bwiedermann/piconot-external/blob/master/src/main/scala/vanillabot/syntax/Parser.scala
[Main]: https://github.com/bwiedermann/piconot-external/blob/master/src/main/scala/vanillabot/vanillabot.scala
[Checks]: https://github.com/bwiedermann/piconot-external/blob/master/src/main/scala/semantics/checks.scala

# Ben's external piconot: Vanillabot

This implementation is of the "Vanilla" CS 5 Picobot syntax. The focus is on error 
handling (rather than on syntax or AST design). In particular, the
implementation includes:

  - a [parser][Parser] with a bit of error handling
  - a [main program][Main], with lots of error handling
  - four specific [error checkers][Checks]: one for undefined states, one for
  unreachable states, one for states that are all surrounded by walls, and one
  for a rule that tries to move in the direction where there's a wall.

What's *missing* from this submission is tests, an IR, and all the auxilary 
files (e.g., grammar files) :(. The IR is "missing" because the picolib library
elements serve as the IR.

To run the program, go to the top-level directory (the one with build.sbt) and
execute:
```
sbt "run maze-file program-file"
```

For example, you can run a bot on the empty maze by executing:
```
sbt "run resources/empty.txt empty-prog.txt"
```
