# NARRATIVE

Narrative is a testing assertion library that implements logging first development. The logging assertion should be appended at the end of Acceptance tests that exercise enough complexity to conform a juicy narrative. Let's see an example for ScalaTest.

```scala
class BookingTrainSpec extends Narratives

"Booking train system" should {
    "abort the transaction when paying gateway timeouts” in {
          givenPaypalIsDown()
          val response = whenUserPaysUsingPaypal()
          response.message shouldBe “Problem connecting with Paypal"
          loggingNarrativeShouldBe {
               - Validating an "internationalTransaction" payment for a country called "Spain".
               - Retrieving accessToken from Paypal for a user identified with "1243-abcd" userId.
               - Sending payment to Paypal in behalf of a user identified with "1243-abcd" userId and with "sad-7898" paymentId.
               - Paypal platform timeout when processing a payment in behalf of a user identified with "1243-abcd" userId and with "sad-7898" paymentId.
               - Updating payment identified with "sad-7898" paymentId with status "ABORTED".
          }
     }
}
```

## PHILOSOPHY

Logging should be a first class citizen in every system that aims to be easily diagnosed and maintained. Logging after the production code has been done is boring (as with testing), so developers tend to miss this duty. At the same time, logging/testing first could help you to drive production code. Logging makes sense in context, we called that context narrative. Achieving the sweet spot of just enough logging is really hard, and thinking in narrative could help you to figure out gaps or noises. At the same time it’s easy to log objects that includes private information like passwords or tokens and not realising of that until we actually read production logs.

Those tests have a lot info about the implementation of your design and that could make further refactors harder, but that’s the same problem that you could face with some style of unit testing. If that’s a concern for you, you could delete those tests after they’ve helped you writing proper logging. Just to summarise, this logging first development could give you the following benefits:

- Help you to come up with some useful logging that makes sense in context, that exposes enough, and just enough, semantic information and that doesn’t leak secure information.
- Help you to understand beforehand what are the high level technical details that you’re design will implement.
- Provide technical documentation at acceptance testing level with a business language
- Provide insights to security, support or operations engineers that could have different needs and drivers that application developers.

## THEATRIC MODE

You can configure Narrative in theatric mode. This modes gives more information about who was logging and with which intensity. It provides a subject, the class, and a verb, the logging level converted to verb in 3rd person of singular, for the object that is the log message:

```scala
implicit val narrativeMode: NarrativeMode = NarrativeModes.THEATRIC

loggingNarrativeShouldBe {
     PaymentValidator informs       - Validating an “internationalTransaction" payment for a country called "Spain".
     PaypalClient informs               - Retrieving accessToken from Paypal for a user identified with "1243-abcd" userId.
     PaypalClient informs               - Sending payment to Paypal in behalf of a user identified with "1243-abcd" userId and with "sad-7898" paymentId.
     PaypalClient errors                  - Paypal platform timeout when processing a payment in behalf of a user identified with v1243-abcd" userId and with "sad-7898" paymentId.
     PaymentRepository informs    - Updating payment identified with "sad-7898" paymentId with status "ABORTED".
}
```
