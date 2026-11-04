package parking_lot.entity.vehicles;

import parking_lot.enums.VehicleType;

public class Bike extends Vehicle {
    public Bike (String vehicleNumber) {
        super (vehicleNumber);
    }
    @Override 
    public VehicleType getType () {
        return VehicleType.BIKE;
    }
}
