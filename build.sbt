name := "crypto"

version := "0.1"

scalaVersion := "2.13.5"

val coinGeckoApiDependencies = Seq (
  "com.lihaoyi" %% "ujson" % "1.2.3",
  "com.lihaoyi" %% "requests" % "0.6.5"
)

lazy val cliParser = project.in(file("cli-parser" ))
lazy val coinGeckoAPI = project.in(file("coingecko-api"))
  .settings( libraryDependencies ++= coinGeckoApiDependencies )

lazy val root = project.in(file(".")).dependsOn(cliParser, coinGeckoAPI).aggregate(cliParser, coinGeckoAPI)
mainClass in (Compile, run) := Some("com.knoldus.CryptoPrice")