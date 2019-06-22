lazy val root = (project in file("."))
  .settings(
    name := "sbt-test",
    version := "0.1.0",
    scalaVersion := "2.13.0",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint"),
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.2.27",
      "com.chuusai" %% "shapeless" % "2.3.3",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test",
      )
  )
