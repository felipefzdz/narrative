package com.github.felipefzdz.example

import com.github.felipefzdz.narrative.{NarrativeMode, THEATRIC, Narrative}
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TheatricExampleSpec extends WordSpec with Narrative {
  override val narrativeScope: String = "com.github.felipefzdz.example"
  implicit val narrativeMode: NarrativeMode = THEATRIC

  val example = new TheatricExample(new PaymentValidator(), new PaypalClient(), new PaymentRepository())

  "Booking train system" should {
    "abort the transaction when paying gateway timeouts" in {
      example.book()
      loggingNarrativeShouldBe (
        "PaymentValidator informs - Validating an `internationalTransaction` payment for a country called `Spain`." ,
        "PaypalClient informs - Retrieving accessToken from Paypal for a user identified with `1243-abcd` userId.",
        "PaypalClient informs - Sending payment to Paypal in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.",
        "PaypalClient errors - Paypal platform timeout when processing a payment in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.",
        "PaymentRepository informs - Updating payment identified with `sad-7898` paymentId with status `ABORTED`."
      )
    }
  }


}