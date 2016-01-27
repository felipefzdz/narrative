package com.narrative

import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory

@RunWith(classOf[JUnitRunner])
class NarrativeSpec extends WordSpec with Narrative {

  val log: org.slf4j.Logger = LoggerFactory.getLogger(classOf[NarrativeSpec])
  implicit val logger: Logger = new Logger()

  "Narrative" should {
    "pass when the expected lines are logged" in {
      log.info("Hello from the logs!")
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
