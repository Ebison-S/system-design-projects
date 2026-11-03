package parking_lot.enums;

public enum PaymentStatus {
    IN_PROGRESS,
    SUCCESS,
    FAILED;

    public boolean canTransitionTo (PaymentStatus next) {
        switch (this) {
            case IN_PROGRESS: return next == SUCCESS || next == FAILED;
            default: return false;
        }
    }
}
