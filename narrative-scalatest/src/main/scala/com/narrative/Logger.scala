package com.narrative

import scala.collection.mutable.ListBuffer

class Logger() {
  var logs: ListBuffer[String] = new ListBuffer[String]()

  def info(message: String) = {
    logs += message
  }

  def getLogs(): ListBuffer[String] = {
    logs
  }
}
