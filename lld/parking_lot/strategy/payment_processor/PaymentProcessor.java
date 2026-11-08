package lld.parking_lot.strategy.payment_processor;

import lld.parking_lot.entity.PaymentResult;

public interface PaymentProcessor {
    public PaymentResult pay (Double amount);
}
