package lld.parking_lot.enums;

public enum TicketStatus {
    ACTIVE,
    PAYMENT_PENDING,
    PAID,
    CLOSED;

    public boolean canTransitionTo (TicketStatus next) {
        switch (this) {
            case ACTIVE: return next == PAYMENT_PENDING;
            case PAYMENT_PENDING: return next == PAID;
            case PAID: return next == CLOSED;
            default: return false;
        }
    }
}
