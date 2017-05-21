lazy val root = (project in file("."))
  .settings(
    name := "sbt-test",
    version := "0.1.0",
    scalaVersion := "2.12.2",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint"),
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.2.12",
      "com.chuusai" %% "shapeless" % "2.3.2",
      "org.typelevel" %% "cats" % "0.9.0",
      "org.scalatest" %% "scalatest" % "3.0.2" % "test",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test"
    )
  )
