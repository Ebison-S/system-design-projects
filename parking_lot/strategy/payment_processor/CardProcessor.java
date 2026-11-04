package parking_lot.strategy.payment_processor;

import java.util.UUID;

import parking_lot.entity.PaymentResult;
import parking_lot.enums.PaymentStatus;

public class CardProcessor implements PaymentProcessor {
    @Override 
    public PaymentResult pay (Double amount) {
        return PaymentResult.withTransactionId (
                PaymentStatus.SUCCESS, UUID.randomUUID ().toString ());
    }
}
