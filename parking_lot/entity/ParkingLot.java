package parking_lot.entity;

import java.util.List;

public class ParkingLot {
    private final String parkingLotId;
    private final List <Floor> floors;

    public ParkingLot (String parkingLotId, List <Floor> floors) {
        this.parkingLotId = parkingLotId;
        this.floors = floors;
    }

    public String getParkingLotId () { return parkingLotId; }

    public List <Floor> getFloors () {
        return floors;
    }
}
