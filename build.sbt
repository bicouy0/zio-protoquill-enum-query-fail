val scala3Version = "3.2.1"
val zioVersion = "2.0.4"
val protoquillVersion = "4.6.0"
val logbackVersion = "1.2.11"

lazy val root = project
  .in(file("."))
  .settings(
    name := "zio-protoquill-enum",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      "io.getquill" %% "quill-jdbc-zio" % protoquillVersion,
      "org.xerial" % "sqlite-jdbc" % "3.40.0.0",
      "ch.qos.logback"         % "logback-classic"  % logbackVersion,
    ),

    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
