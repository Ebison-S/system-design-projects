package parking_lot.entity.vehicles;

import parking_lot.enums.VehicleType;

public class Car extends Vehicle {
    public Car (String vehicleNumber) {
        super (vehicleNumber);
    }
    
    @Override 
    public VehicleType getType () {
        return VehicleType.CAR;
    }
}
