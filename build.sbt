name := """active-slick-dynamic-provider"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.0.2",
  "io.strongtyped" %% "active-slick" % "0.3.2-SNAPSHOT",
  "org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
  "com.h2database" % "h2" % "1.3.170",
  "junit" % "junit" % "4.10" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

EclipseKeys.withSource := true
