name := "ScalaAkka"

version := "0.1"

resolvers += "Typsafe Repository" at "http://repo.typesafe.com/typesafe/releases"

resolvers += "Typsafe Repository" at "http://repo.typesafe.com/typesafe/snapshots"

scalaVersion := "2.9.1"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

exportJars := true

mainClass in oneJar := Some("com.scalaakka.Main")

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor" % "2.0-RC2" % "compile",
  "com.typesafe.akka" % "akka-remote" % "2.0-RC2" % "compile",
  "com.typesafe.akka" % "akka-testkit" % "2.0-RC2" % "compile",
  "com.typesafe.akka" % "akka-slf4j" % "2.0-RC2" % "compile",
  "ch.qos.logback"    % "logback-classic" % "1.0.0" % "compile->default",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test",
  "commons-lang" % "commons-lang" % "2.6"
)
