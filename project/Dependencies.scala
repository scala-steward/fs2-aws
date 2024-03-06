import sbt.Keys._
import sbt.{Def, _}

//noinspection TypeAnnotation
object Dependencies {

  object V {
    val AwsSdk           = "2.24.13"
    val Circe            = "0.14.5"
    val Munit            = "0.7.29"
    val Fs2              = "3.9.4"
    val Refined          = "0.11.1"
    val ScalaTest        = "3.2.18"
    val MockitoScalaTest = "1.17.5"
    val MockitoCore      = "5.10.0"
    val CE               = "3.5.3"
    val Logback          = "1.5.0"
    val SLF4J            = "2.0.12"
    val Log4Cats         = "2.6.0"
  }

  val Fs2Core = libraryDependencies ++= Seq(
    "co.fs2" %% "fs2-core" % V.Fs2,
    "co.fs2" %% "fs2-io"   % V.Fs2
  )

  val CatsEffect = libraryDependencies += "org.typelevel" %% "cats-effect" % V.CE

  val KinesisClient = libraryDependencies += "software.amazon.kinesis" % "amazon-kinesis-client" % "2.5.5"

  val KinesisProducer = libraryDependencies += "com.amazonaws" % "amazon-kinesis-producer" % "0.15.8"

  val newTypes = libraryDependencies += "io.monix" %% "newtypes-core" % "0.2.3"

  def AWS(artifact: String, config: Configuration = Compile): Def.Setting[Seq[ModuleID]] =
    libraryDependencies += "software.amazon.awssdk" % artifact % V.AwsSdk % config

  val Refined = libraryDependencies += "eu.timepit" %% "refined" % V.Refined

  val Testing = libraryDependencies ++= (
    Seq(
      "org.scalameta" %% "munit"               % V.Munit,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7",
      "org.scalatest" %% "scalatest"           % V.ScalaTest,
      "org.mockito"    % "mockito-core"        % V.MockitoCore,
      "org.typelevel" %% "cats-effect"         % V.CE,
      "javax.xml.bind" % "jaxb-api"            % "2.3.1"
    )
      ++ LogModules.LogBack
      ++ LogModules.LogImpl
  ).map(_ % Test)

  val Circe = libraryDependencies ++= Seq(
    "io.circe" %% "circe-core"    % V.Circe,
    "io.circe" %% "circe-generic" % V.Circe,
    "io.circe" %% "circe-parser"  % V.Circe
  )

  val ScalaTest = libraryDependencies += "org.scalatest" %% "scalatest" % V.ScalaTest

  val Mockito = libraryDependencies += "org.mockito" % "mockito-core" % V.MockitoCore

  // TODO: it seems that different modules require different versions - look into this
  def ScanamoCirce(version: String) = libraryDependencies ++= Seq(
    "io.laserdisc" %% "scanamo-circe"
      % version
  ).filterNot(_ => scalaVersion.value.startsWith("3."))

  val DynamoStreamAdapter =
    libraryDependencies += "com.amazonaws" % "dynamodb-streams-kinesis-adapter" % "1.6.0"

  val Logging = libraryDependencies ++= Seq(
    "org.typelevel" %% "log4cats-slf4j" % V.Log4Cats
  ) ++ LogModules.LogBack ++ LogModules.LogImpl

  val Logback = libraryDependencies ++= LogModules.LogBack

  val Trace4Cats = libraryDependencies +=
    "io.janstenpickle" %% "trace4cats-core" % "0.14.7"

  // save redefining these over and over
  private[this] object LogModules {
    val LogBack = Seq(
      "ch.qos.logback" % "logback-classic" % V.Logback,
      "ch.qos.logback" % "logback-core"    % V.Logback
    )
    val LogImpl = Seq(
      "org.slf4j" % "jcl-over-slf4j" % V.SLF4J,
      "org.slf4j" % "jul-to-slf4j"   % V.SLF4J
    )
  }

}
