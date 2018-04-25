name := "AdserverPractice"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= {
  val akkaVersion = "2.5.12"
  val akkaHttpVersion = "10.1.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"           % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit"         % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
    "org.scalatest"     %% "scalatest"            % "3.0.5"
    //使い方を理解してからコメントを外すこと
    //"com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    //"com.typesafe.akka" %% "akka-slf4j"           % akkaVersion,
    //"ch.qos.logback"    %  "logback-classic       % "1.2.3",
  )
}
