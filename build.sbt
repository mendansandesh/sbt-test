lazy val root = (project in file(".")).
  settings(
    name := "sbt-test",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.12.11",
    organization := "com.colortokens.appsec"
  )

//java 11 - hadoop 3.0
//java  8 - hadoop 2.7

val sparkVersion = "3.0.0"

// %% - scala dependencies & % for java dependencies
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",   //"provided" : not incl in the final fat jar
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion % "provided",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
  "org.mongodb.scala" %% "mongo-scala-bson" % "2.9.0",
  "org.mongodb" % "bson" % "3.12.2",
  "org.mongodb" % "mongodb-driver-core" % "3.12.2",
  "org.mongodb" % "mongodb-driver-async" % "3.12.2",
  "org.scalactic" %% "scalactic" % "3.2.0",
  "org.scalatest" %% "scalatest" % "3.2.0" % "test"
)

//customizes the name of the JAR file for 'sbt package'
artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "_" + sv.binary + "-" + sparkVersion + "_" + module.revision + "." + artifact.extension
}

publishTo := Some("Artifactory Realm" at "https://artifactory.colortokens.com/artifactory/gradle-release-local")

credentials += Credentials("Artifactory Realm", "artifactory.colortokens.com", "appsec-ana", "AP6oyj89mCgv1hiT2bpmkc6q7wjPjgsVwNgvaw")

parallelExecution in Test := false
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
