package com.narrative

import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.exceptions.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.slf4j.{Logger, LoggerFactory}
import uk.org.lidalia.slf4jtest.{TestLogger, TestLoggerFactory}

@RunWith(classOf[JUnitRunner])
class NarrativeSpec extends WordSpec with Narrative {

  implicit val logger: TestLogger = TestLoggerFactory.getTestLogger(classOf[SystemUnderTest]);

  "Narrative" should {
    "pass when the expected lines are logged" in {
      new SystemUnderTest().triggerGreetings()
      loggingNarrativeShouldBe(
        "Hi",
        "Hola",
        "Ciao"
      )
    }

    "fail when the expected lines are not logged" in {
      new SystemUnderTest().triggerGreetings()
      val thrown = intercept[TestFailedException] {
        loggingNarrativeShouldBe(
          "I'm grumpy today, no greetings"
        )
      }
      thrown.getMessage() should include ("was not equal to")
    }
  }
}

class SystemUnderTest() {
  val logger: Logger = LoggerFactory.getLogger(classOf[SystemUnderTest]);

  def triggerGreetings(): Unit = {
    logger.info("Hi")
    logger.info("Hola")
    logger.info("Ciao")
  }

}
