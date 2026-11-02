package parking_lot.entity;

import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.SpotStatus;
import parking_lot.enums.SpotType;

public class Spot {
    String spotId;
    String floorId;
    SpotType spotType;
    SpotStatus spotStatus;
    Vehicle vehicle;
}
