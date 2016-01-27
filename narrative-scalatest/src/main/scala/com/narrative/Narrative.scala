package com.narrative

import org.scalatest.Matchers

trait Narrative extends Matchers {

  def loggingNarrativeShouldBe(sentences: String*)(implicit logger: Logger): Unit = {
    sentences shouldBe logger.getLogs
  }

}
