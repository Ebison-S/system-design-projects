package lld.parking_lot.strategy.payment_processor;

import java.util.UUID;

import lld.parking_lot.entity.PaymentResult;
import lld.parking_lot.enums.PaymentStatus;

public class CardProcessor implements PaymentProcessor {
    @Override 
    public PaymentResult pay (Double amount) {
        return PaymentResult.withTransactionId (
                PaymentStatus.SUCCESS, UUID.randomUUID ().toString ());
    }
}
