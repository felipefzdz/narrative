package com.narrative

sealed trait NarrativeMode

case object PROSE extends NarrativeMode
case object THEATRIC extends NarrativeMode

case class Sentence(character: String, verb: String, speech: String)
