package parking_lot.service;

import parking_lot.entity.PaymentResult;
import parking_lot.strategy.payment_processor.PaymentProcessor;

public class PaymentService {
    private final PaymentProcessor paymentProcessor;

    public PaymentService (PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public PaymentResult processPayment (Double amount) {
        PaymentResult paymentResult = paymentProcessor.pay (amount);
        return paymentResult;
    }
}
