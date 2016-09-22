name := "jetty"

version := "1.0"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-plus
libraryDependencies += "org.eclipse.jetty" % "jetty-plus" % "9.2.19.v20160908"
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-server
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.2.19.v20160908"
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-http
libraryDependencies += "org.eclipse.jetty" % "jetty-http" % "9.2.19.v20160908"
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-io
libraryDependencies += "org.eclipse.jetty" % "jetty-io" % "9.2.19.v20160908"
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-servlet
libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.2.19.v20160908"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

// https://mvnrepository.com/artifact/javax.servlet/servlet-api
libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"

assemblyMergeStrategy in assembly := {
  case x if x.startsWith("META-INF") => MergeStrategy.discard // Bumf
  case x if x.endsWith(".html") => MergeStrategy.discard // More bumf
  case x if x.contains("slf4j-api") => MergeStrategy.last
  case x if x.contains("slf4j") => MergeStrategy.first
  case x if x.contains("commons") => MergeStrategy.first
  case x if x.contains("web-app") => MergeStrategy.first
  case x if x.contains("datatypes") => MergeStrategy.first
  case x if x.contains("XMLSchema") => MergeStrategy.first
  case x if x.contains("xsd") => MergeStrategy.first
  case x if x.contains("Http") => MergeStrategy.first
  case x if x.contains("org/cyberneko/html") => MergeStrategy.first
  case x if x.contains("parquet") => MergeStrategy.first
  case x if x.contains("class") => MergeStrategy.first
  case x if x.contains("properties") => MergeStrategy.first
  case x if x.contains("xml") => MergeStrategy.first
  case PathList("com", "esotericsoftware", xs@_ *) => MergeStrategy.last // For Log$Logger.class
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

publishArtifact in Test := false
    