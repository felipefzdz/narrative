package com.github.felipefzdz.narrative

import org.scalatest.{BeforeAndAfterEach, Matchers, Suite}
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jext.Level._
import uk.org.lidalia.slf4jtest.TestLoggerFactory._
import uk.org.lidalia.slf4jtest.{LoggingEvent, TestLogger, TestLoggerFactory}

import scala.collection.JavaConversions._

trait Narrative extends Matchers with BeforeAndAfterEach {
  this: Suite =>

  val narrativeScope: String

  override def beforeEach(): Unit = {
    TestLoggerFactory.clearAll()
  }

  def loggingNarrativeShouldBe(expectedSentences: String*)(implicit narrativeMode: NarrativeMode = PROSE): Unit = {
    implicit val loggingEvents: Seq[LoggingEvent] = getAllLoggingEvents.toList
      .filter(_.getCreatingLogger.getName.startsWith(narrativeScope))

    narrativeMode match {
      case PROSE => proseNarrativeShouldBe(expectedSentences)
      case THEATRIC => theatricNarrativeShouldBe(expectedSentences)
    }
  }

  private def proseNarrativeShouldBe(expectedSentences: Seq[String])(implicit loggingEvents: Seq[LoggingEvent]) = {
    expectedSentences shouldBe loggingEvents.map(_.getMessage)
  }

  private def theatricNarrativeShouldBe(expectedSentences: Seq[String])(implicit loggingEvents: Seq[LoggingEvent]) = {
    val actualSentences = loggingEvents.map(loggingEvent =>
      Sentence(
        character = loggingEvent.getCreatingLogger,
        verb = loggingEvent.getLevel,
        speech = loggingEvent.getMessage)
    )
    toTypedSentences(expectedSentences) shouldBe actualSentences
  }

  private implicit def toThirdPersonSingularSimplePresent(level: Level): String = {
    level match {
      case TRACE => "traces"
      case DEBUG => "debugs"
      case INFO => "informs"
      case WARN => "warns"
      case ERROR => "errors"
      case OFF => "offs"
    }
  }

  private implicit def toTestLoggerName(testLogger: TestLogger): String = {
    testLogger.getName.replace(s"${narrativeScope}.", "")
  }

  private def toTypedSentences(sentences: Seq[String]): Seq[Sentence] = {
    sentences.map(sentence => {
      val escapedSentence = sentence.replaceFirst("- ", "")
      val tokens = escapedSentence.split(" ")
      val character = tokens(0)
      val verb = tokens(1)
      val speech = escapedSentence.replace(character, "").replace(verb, "").trim
      Sentence(character = character, verb = verb, speech = speech)
    })
  }

}
