name := "test"

version := "0.1"

scalaVersion := "2.13.2"

val http4sVersion = "0.21.4"
val circeVersion = "0.12.3"
val catsVersion = "2.1.3"
val scalatestVersion = "3.1.2"
val scanamoVersion = "1.0.0-M12"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % catsVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.scanamo" %% "scanamo" % scanamoVersion,

  "org.scanamo" %% "scanamo-testkit" % scanamoVersion % "test",
  "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)
