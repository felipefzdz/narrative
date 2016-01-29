package com.narrative

import org.scalatest.Matchers
import uk.org.lidalia.slf4jtest.TestLoggerFactory

import scala.collection.JavaConversions._

trait Narrative extends Matchers {
  val narrativeScope: String

  def loggingNarrativeShouldBe(expectedSentences: String*): Unit = {
    val actualSentences = TestLoggerFactory.getAllTestLoggers.toMap
      .filterKeys(loggerName => loggerName.startsWith(narrativeScope)).values
      .flatMap(_.getAllLoggingEvents.toList)
      .map(event => event.getMessage)
    expectedSentences shouldBe actualSentences
  }

}
