val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Hello World",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    scalacOptions += "-experimental",
    libraryDependencies += "io.github.neotypes" %% "neotypes-core" % "1.0.0",
    libraryDependencies += "io.github.neotypes" %% "neotypes-generic" % "1.0.0",
    libraryDependencies += "org.neo4j.driver" % "neo4j-java-driver" % "5.19.0",
    libraryDependencies += "dev.soundness" % "vacuous-core" % "0.1.0",
    libraryDependencies += "dev.soundness" % "rudiments-core" % "0.1.0",
    libraryDependencies += "dev.soundness" % "dendrology-tree" % "0.2.0"
  )
