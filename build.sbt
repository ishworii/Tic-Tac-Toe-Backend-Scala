scalaVersion := "2.13.12"

val AkkaVersion = "2.8.8"
val AkkaHttpVersion = "10.5.3"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "io.spray" %% "spray-json" % "1.3.6",
    "ch.megard" %% "akka-http-cors" % "1.2.0"
)