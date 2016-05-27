package com.github.felipefzdz.example

import com.github.felipefzdz.narrative.{PROSE, NarrativeMode, Narrative}
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ProseExampleSpec extends WordSpec with Narrative {
 override val narrativeScope: String = "com.github.felipefzdz.example"
 implicit val narrativeMode: NarrativeMode = PROSE

 val example = new ProseExample()

 "Booking train system" should {
  "abort the transaction when paying gateway timeouts" in {
   example.book()
   loggingNarrativeShouldBe (
    "Validating an `internationalTransaction` payment for a country called `Spain`." ,
    "Retrieving accessToken from Paypal for a user identified with `1243-abcd` userId.",
    "Sending payment to Paypal in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.",
    "Paypal platform timeout when processing a payment in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.",
    "Updating payment identified with `sad-7898` paymentId with status `ABORTED`."
   )
  }
 }
}
