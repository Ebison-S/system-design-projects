package lld.parking_lot.strategy.payment_processor;

import lld.parking_lot.entity.PaymentResult;
import lld.parking_lot.enums.PaymentStatus;

public class CashProcessor implements PaymentProcessor {
    @Override 
    public PaymentResult pay (Double amount) {
        return PaymentResult.withoutTransactionId (PaymentStatus.SUCCESS);
    }
}
