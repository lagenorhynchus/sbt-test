lazy val root = (project in file("."))
  .settings(
    name := "sbt-test",
    version := "0.1.0",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint"),
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.1.0",
      "com.chuusai" %% "shapeless" % "2.1.0",
      "org.typelevel" %% "cats" % "0.6.0",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
      "com.lihaoyi" %% "ammonite-repl" % "0.6.0" % "test" cross CrossVersion.full
    ),
    initialCommands in (Test, console) := """ammonite.repl.Main().run()"""
  )
