name := "External Piconot"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= 
  Seq( "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
       "org.scalatest" % "scalatest_2.11" % "2.1.7",
       "org.scala-lang" % "scala-compiler" % scalaVersion.value,
       "org.scalafx" % "scalafx_2.11" % "8.0.5-R5" )
