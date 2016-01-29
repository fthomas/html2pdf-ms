enablePlugins(MdocPlugin)

name := "html2pdf"
version := "0.0.1"
description := "microservice for converting HTML to PDF"

resolvers += "Frank's Bintray" at "https://dl.bintray.com/fthomas/maven"

val http4sVersion = "0.12.0"
libraryDependencies ++= Seq(
  "eu.timepit" %% "properly" % "0.0.0-18-g5fef5a3",
  "org.http4s" %% "http4s-core" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.m-doc" %% "fshell" % "0.0.0-10-g4a61b77",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

Revolver.settings

// sbt-native-packager
enablePlugins(JavaServerAppPackaging)
maintainer := "Frank S. Thomas <frank@timepit.eu>"
packageSummary := description.value
packageDescription := s"See <${homepage.value.get}> for more information."

// deb settings
enablePlugins(DebianPlugin)
debianPackageDependencies in Debian ++= Seq("wkhtmltopdf", "xvfb", "logrotate")
serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV

// rpm settings
enablePlugins(RpmPlugin)
rpmVendor := organization.value
rpmLicense := licenses.value.headOption.map(_._1)
rpmGroup := Some("System Environment/Daemons")
rpmBrpJavaRepackJars := true

addCommandAlias("validate", Seq(
  "clean",
  "coverage",
  "test",
  "scalastyle",
  "test:scalastyle",
  "doc",
  "debian:packageBin",
  "rpm:packageBin"
).mkString(";", ";", ""))
