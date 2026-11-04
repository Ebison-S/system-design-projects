package parking_lot.entity;

import java.util.Optional;

import parking_lot.enums.PaymentStatus;

public class PaymentResult {
    private final PaymentStatus paymentStatus;
    private final String transactionId;

    private PaymentResult (PaymentStatus paymentStatus, String transactionId) {
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
    }

    public static PaymentResult withTransactionId (PaymentStatus paymentStatus, String transactionId) {
        return new PaymentResult (paymentStatus, transactionId);
    }

    public static PaymentResult withoutTransactionId (PaymentStatus paymentStatus) {
        return new PaymentResult (paymentStatus, null);
    }

    public PaymentStatus getPaymentStatus () { return paymentStatus; }

    public Optional <String> getTransactionId () { return Optional.ofNullable (transactionId); }
}
