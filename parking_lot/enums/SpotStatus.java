package parking_lot.enums;

public enum SpotStatus {
    AVAILABLE,
    OCCUPIED;

    public boolean canTransitionTo (SpotStatus next) {
        switch (this) {
            case AVAILABLE: return next == OCCUPIED;
            case OCCUPIED: return next == AVAILABLE;
            default: return false;
        }
    }
}
