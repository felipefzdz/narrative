package com.narrative

import org.scalatest.Matchers

import scala.collection.mutable.ListBuffer

trait Narrative extends Matchers {

  def loggingNarrativeShouldBe(sentences: ListBuffer[String])(implicit logger: Logger): Unit = {
    sentences shouldBe (logger.getLogs)
  }

}
