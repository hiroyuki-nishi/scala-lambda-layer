import sbt._

object Dependencies {
  // aws
  lazy val awsSdkV2Version = "2.13.70"
  lazy val awsDynamoDB = "software.amazon.awssdk" % "dynamodb" % awsSdkV2Version

  // test
  lazy val scalaTestVersion = "3.2.0"

  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest-wordspec" % scalaTestVersion % "test",
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  lazy val dynamoDBDependencies = Seq(awsDynamoDB)
}
