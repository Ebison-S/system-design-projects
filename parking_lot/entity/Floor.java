package parking_lot.entity;

import java.util.List;

public class Floor {
    String floorId;
    List <Spot> spots;

    public Floor (List <Spot> spots) {
        this.spots = spots;
    }

    public List <Spot> getSpots () {
        return spots;
    }
}
