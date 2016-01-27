package com.narrative

import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NarrativeSpec extends WordSpec with Narrative {

  implicit val logger: Logger = new Logger()

  "Narrative" should {
    "pass when the expected lines are logged" in {
      new SystemUnderTest().triggerGreetings()
      loggingNarrativeShouldBe(
        "Hi",
        "Hola",
        "Ciao"
      )
    }
  }

  class SystemUnderTest() {

    def triggerGreetings(): Unit = {
      logger.info("Hi")
      logger.info("Hola")
      logger.info("Ciao")
    }

  }

}
