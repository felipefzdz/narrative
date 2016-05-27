package com.github.felipefzdz.example

import com.typesafe.scalalogging.LazyLogging

class TheatricExample(paymentValidator: PaymentValidator, paypalClient: PaypalClient, paymentRepository: PaymentRepository){

  def book() = {
    paymentValidator.validate()
    paypalClient.pay()
    paymentRepository.update()
  }

}

class PaymentValidator extends LazyLogging {
  def validate() = logger.info("Validating an `internationalTransaction` payment for a country called `Spain`.")
}

class PaypalClient extends LazyLogging {
  def pay() = {
    logger.info("Retrieving accessToken from Paypal for a user identified with `1243-abcd` userId.")
    logger.info("Sending payment to Paypal in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.")
    logger.error("Paypal platform timeout when processing a payment in behalf of a user identified with `1243-abcd` userId and with `sad-7898` paymentId.")
  }
}

class PaymentRepository extends LazyLogging {
  def update() = logger.info("Updating payment identified with `sad-7898` paymentId with status `ABORTED`.")
}