name := "AdserverPractice"

val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.5",
  libraryDependencies ++= {
    val akkaVersion = "2.5.12"
    val akkaHttpVersion = "10.1.1"
    Seq(
      "com.typesafe.akka" %% "akka-actor"              % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit"            % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http"               % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-testkit"       % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-stream"             % akkaVersion,
      "com.typesafe.akka" %% "akka-stream-testkit"     % akkaVersion % Test,
      "org.scalatest"     %% "scalatest"               % "3.0.5",
      "com.typesafe"      %  "config"                  % "1.3.2",
      "com.typesafe.akka" %% "akka-http-spray-json"    % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-remote"             % akkaVersion,
      "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % Test,
      //使い方を理解してからコメントを外すこと
      "com.typesafe.akka" %% "akka-slf4j"              % akkaVersion,
      "ch.qos.logback"    %  "logback-classic"         % "1.2.3",
      "net.debasishg"     %% "redisclient"             % "3.7"
    )
  }
)

lazy val common               = (project in file("common")).settings(
  organization := "scala.example.common",
  commonSettings
).enablePlugins(FlywayPlugin)

lazy val adserver             = (project in file("adserver")).settings(
  organization := "scala.example.adserver",
  commonSettings
).dependsOn(common)

lazy val recommendserver      = (project in file("recommendserver")).settings(
  organization := "scala.example.recommendserver",
  commonSettings
).dependsOn(common)

lazy val widgetdeliveryserver = (project in file("widgetdeliveryserver")).settings(
  organization := "scala.example.widgetdeliveryserver",
  commonSettings
).dependsOn(common)