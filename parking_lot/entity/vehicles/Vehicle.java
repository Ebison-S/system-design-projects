package parking_lot.entity.vehicles;

import java.util.UUID;

import parking_lot.enums.VehicleType;

public abstract class Vehicle {
    String vehicleId;
    String vehicleNumber;

    public Vehicle (String vehicleNumber) {
        this.vehicleId = UUID.randomUUID ().toString ();
        this.vehicleNumber = vehicleNumber;
    }

    public abstract VehicleType getType ();
}
