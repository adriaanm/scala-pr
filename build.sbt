scalaVersion := "2.12.2-bin-SHA7dig-SNAPSHOT"


resolvers += "pr" at "https://scala-ci.typesafe.com/artifactory/scala-pr-validation-snapshots/"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

fork in ThisBuild := true

def mkCommand(entryPoint: String, mainClassName: String, baseDir: File) =
  Command(entryPoint)(_ => scala.build.ScalaOptionParser.scalaParser(entryPoint, baseDir)) { (state, parsedOptions) =>
    ("runMain " + mainClassName + " -usejavacp " + parsedOptions) :: state
  }

commands += mkCommand("scalac",    "scala.tools.nsc.Main", (baseDirectory in ThisBuild).value)
commands += mkCommand("scala",     "scala.tools.nsc.MainGenericRunner", (baseDirectory in ThisBuild).value)
commands += mkCommand("scaladoc",  "scala.tools.nsc.ScalaDoc", (baseDirectory in ThisBuild).value)
