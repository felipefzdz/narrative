package com.narrative

import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.exceptions.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.slf4j.{Logger, LoggerFactory}

@RunWith(classOf[JUnitRunner])
class NarrativeSpec extends WordSpec with Narrative {

  override val narrativeScope = "com.narrative"

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
      thrown.getMessage() should include("was not equal to")
    }
  }
}

class SystemUnderTest() {
  val logger: Logger = LoggerFactory.getLogger(classOf[SystemUnderTest])
  val loggerForLibraryPackages = LoggerFactory.getLogger(classOf[WordSpec])

  def triggerGreetings(): Unit = {
    logger.info("Hi")
    loggerForLibraryPackages.info("Salut")
    logger.info("Hola")
    logger.info("Ciao")
  }

}
