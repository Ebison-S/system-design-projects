package parking_lot.entity.vehicles;

import parking_lot.enums.VehicleType;

public abstract class Vehicle {
    String vehicleId;
    String vehicleNumber;

    public abstract VehicleType getType ();
}
