package com.narrative

import org.scalatest.Matchers
import uk.org.lidalia.slf4jtest.TestLogger

import scala.collection.JavaConversions._

trait Narrative extends Matchers {

  def loggingNarrativeShouldBe(expectedSentences: String*)(implicit logger: TestLogger): Unit = {
    val loggingEvents = logger.getAllLoggingEvents.toList
    val actualSentences = loggingEvents.map(event => event.getMessage)
    expectedSentences shouldBe actualSentences
  }

}
