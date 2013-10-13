name := "TwitterStreamer"

scalaVersion := "2.10.3"

organization := "com.streamer.twitter"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.2",
  "org.scala-tools.testing" %% "specs" % "1.6.9",
  "junit" % "junit" % "4.11",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "commons-logging" % "commons-logging" % "1.1.3",
  "org.streum" %% "configrity-core" % "1.0.0",
  "org.json4s" %% "json4s-jackson" % "3.2.5")
 
javaOptions ++= Seq(
  "-XX:MaxPermSize=1g", // This is to prevent [java.lang.OutOfMemoryError: PermGen space]
  "-Xmx2g")

scalacOptions ++= Seq(
  //"-feature",
  //"-deprecation",
  "-unchecked",
  "-Xlint",
  "-Ywarn-adapted-args",
  //"-Ywarn-all",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  //"-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  //"-Ywarn-numeric-widen",
  "-Ywarn-value-discard")

// Add static analysis

resolvers += "linter" at "http://hairyfotr.github.io/linteRepo/releases"

addCompilerPlugin("com.foursquare.lint" %% "linter" % "0.1-SNAPSHOT")

org.scalastyle.sbt.ScalastylePlugin.Settings

// When doing sbt run, fork a separate process.  This is apparently needed by storm.
fork := true

