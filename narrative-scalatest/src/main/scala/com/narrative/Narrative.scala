package com.narrative

import com.github.nscala_time.time.OrderingImplicits._
import org.scalatest.Matchers
import uk.org.lidalia.slf4jtest.TestLoggerFactory._

import scala.collection.JavaConversions._

trait Narrative extends Matchers {
  val narrativeScope: String

  def loggingNarrativeShouldBe(expectedSentences: String*): Unit = {
    val actualSentences =
      getAllTestLoggers.toMap
        .filterKeys(_.startsWith(narrativeScope)).values
        .flatMap(_.getAllLoggingEvents.toList).toList
        .sortBy(_.getTimestamp)
        .map(_.getMessage)
    expectedSentences shouldBe actualSentences
  }

}
