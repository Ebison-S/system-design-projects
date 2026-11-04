package parking_lot.exception;

public class VehicleAlreadyParkedException extends RuntimeException {
    public VehicleAlreadyParkedException (String vehicleNumber) {
        super ("Vehicle " + vehicleNumber + " is already parked");
    }
}
