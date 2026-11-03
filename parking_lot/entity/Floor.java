package parking_lot.entity;

import java.util.List;

public class Floor {
    String floorId;
    List <Spot> spots;

    public List <Spot> getSpots () {
        return spots;
    }
}
