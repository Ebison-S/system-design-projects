package parking_lot.entity.vehicles;

import parking_lot.enums.VehicleType;

class Bike extends Vehicle {
    @Override 
    public VehicleType getType () {
        return VehicleType.BIKE;
    }
}
