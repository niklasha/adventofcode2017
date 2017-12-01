lazy val root = (project in file(".")).
  settings(
    name := "adventofcode2017",
    version := "1.0",
    scalaVersion := "2.12.3"
  )

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test"
