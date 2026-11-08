package lld.parking_lot.entity;

import java.util.UUID;

import lld.parking_lot.entity.vehicles.Vehicle;
import lld.parking_lot.enums.SpotStatus;
import lld.parking_lot.enums.SpotType;

public class Spot {
    private final String spotId;
    private final String floorId;
    private final SpotType spotType;
    private SpotStatus spotStatus;
    private Vehicle vehicle;

    public Spot (
        String floorId,
        SpotType spotType
    ) {
        this.spotId = UUID.randomUUID ().toString ();
        this.floorId = floorId;
        this.spotType = spotType;
        this.spotStatus = SpotStatus.AVAILABLE;
    }

    public boolean isAvailable () {
        return spotStatus == SpotStatus.AVAILABLE;
    }

    public boolean canFit (Vehicle vehicle) {
        switch (vehicle.getType ()) {
            case BIKE: return spotType == SpotType.BIKE;
            case CAR: return spotType == SpotType.COMPACT || spotType == SpotType.LARGE;
            default: return false;
        }
    }

    public void occupy (Vehicle vehicle) {
        transitionTo (SpotStatus.OCCUPIED);
        this.vehicle = vehicle;
    }

    public void vacate () {
        transitionTo (SpotStatus.AVAILABLE);
        this.vehicle = null;
    }

    private void transitionTo (SpotStatus next) {
        if (!spotStatus.canTransitionTo (next))
            throw new IllegalStateException (
                "Spot " + spotId + " cannot move from " + spotStatus + " to " + next);

        this.spotStatus = next;
    }

    public String getSpotId () { return spotId; }
    public String getFloorId () { return floorId; }
    public SpotType getSpotType () { return spotType; }
    public SpotStatus getStatus () { return spotStatus; }
    public Vehicle getVehicle () { return vehicle; }
}
