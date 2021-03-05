
name := "crypto"

version := "0.1"

scalaVersion := "2.12.13"

enablePlugins(JavaAppPackaging)
maintainer := "Arjun Singh <arjun.singh@knoldus.com>"

packageSummary := "Crypto Price test app"

packageDescription := """A fun package description of our software,with multiple lines.""".stripMargin

val coinGeckoApiDependencies = Seq (
  "com.lihaoyi" %% "ujson" % "1.2.3",
  "com.lihaoyi" %% "requests" % "0.6.5"
)

lazy val cliParser = project.in(file("cli-parser" ))
lazy val coinGeckoAPI = project.in(file("coingecko-api"))
  .settings( libraryDependencies ++= coinGeckoApiDependencies )

lazy val root = project.in(file(".")).dependsOn(cliParser, coinGeckoAPI).aggregate(cliParser, coinGeckoAPI)
mainClass in (Compile, run) := Some("com.knoldus.CryptoPrice")