package parking_lot.strategy.payment_processor;

import parking_lot.entity.PaymentResult;

public interface PaymentProcessor {
    public PaymentResult pay (Double amount);
}
