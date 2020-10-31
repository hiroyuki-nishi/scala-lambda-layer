import Dependencies._

organization := "org.me"
name := "scala-common-layer-sample"
//scalaVersion := "2.13.3"
version := "0.1-SNAPSHOT"

lazy val commonSettings = Seq(
//  organization := "jp.lanscopean",
  scalaVersion := "2.13.3",
  scalacOptions := Seq(
    "-deprecation",
    "-feature",
    "-Ywarn-dead-code",
    "-Xfatal-warnings",
    "-target:jvm-1.8"
  ),
  test in assembly := {}
)

val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case _ => MergeStrategy.first
  },
  assemblyJarName in assembly := s"${name.value}.jar"
)

lazy val root = (project in file("."))
  .aggregate(
   infrastructure
  )
  .settings(commonSettings: _*)
  .settings(
    commonSettings
//    publishArtifact := false
  )
  .settings(
    commands += Command.command("assemblyAll") { state =>
      "infrastructure / assembly" ::
        state
    }
  )

lazy val infrastructure = (project in file("modules/adapter/infrastructure"))
  .settings(commonSettings: _*)
  .aggregate(infraDynamoDb)

lazy val infraDynamoDb = (project in file("modules/adapter/infrastructure/dynamodb"))
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*) // TODO: nishi こいつがあると.jarがうまくできない
  .settings(libraryDependencies ++= dynamoDBDependencies ++ testDependencies)
