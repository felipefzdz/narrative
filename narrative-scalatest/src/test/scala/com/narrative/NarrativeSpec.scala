package com.narrative

import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.exceptions.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory

@RunWith(classOf[JUnitRunner])
class NarrativeSpec extends WordSpec with Narrative {

  override val narrativeScope = "com.narrative"

  "Narrative" should {
    "pass when the expected sentences are logged" in {
      new SystemUnderTest().triggerGreetings()

      loggingNarrativeShouldBe(
        "Hi my friend",
        "Hola tronco",
        "Ciao amico"
      )
    }

    "fail when the expected sentences are not logged" in {
      new SystemUnderTest().triggerGreetings()

      val thrown = intercept[TestFailedException] {
        loggingNarrativeShouldBe(
          "I'm grumpy today, no greetings"
        )
      }
      thrown.getMessage() should include("was not equal to")
    }

    "pass when Theatric mode and the sentences are logged " in {
      implicit val narrativeMode: NarrativeMode = THEATRIC

      new SystemUnderTest().triggerGreetings()

      loggingNarrativeShouldBe(
        "SystemUnderTest informs - Hi my friend",
        "Collaborator warns - Hola tronco",
        "SystemUnderTest errors - Ciao amico"
      )
    }

     "fail when Theatric mode and the sentences logged are not matching " in {
      implicit val narrativeMode: NarrativeMode = THEATRIC

      new SystemUnderTest().triggerGreetings()

       val thrown = intercept[TestFailedException] {
         loggingNarrativeShouldBe(
           "SystemUnderTest informs - Something different"
         )
       }
       thrown.getMessage() should include("was not equal to")
    }
  }
}

class SystemUnderTest() extends Collaborator {
  val logger = LoggerFactory.getLogger(classOf[SystemUnderTest])
  val loggerForLibraryPackages = LoggerFactory.getLogger(classOf[WordSpec])

  def triggerGreetings() = {
    logger.info("Hi my friend")
    loggerForLibraryPackages.info("Salut mon amie")
    triggerCollaboratorGreetings
    logger.error("Ciao amico")
  }

}

trait Collaborator {
  val collaboratorLogger = LoggerFactory.getLogger(classOf[Collaborator])

  def triggerCollaboratorGreetings() = {
    collaboratorLogger.warn("Hola tronco")
  }
}
