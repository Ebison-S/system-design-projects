package parking_lot.entity.vehicles;

import java.util.UUID;

import parking_lot.enums.VehicleType;

public abstract class Vehicle {
    private final String vehicleId;
    private final String vehicleNumber;

    protected Vehicle (String vehicleNumber) {
        this.vehicleId = UUID.randomUUID ().toString ();
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleId () { return vehicleId; }

    public String getVehicleNumber () { return vehicleNumber; }

    public abstract VehicleType getType ();
}
