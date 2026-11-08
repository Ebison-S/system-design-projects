package lld.parking_lot.entity;

import java.util.List;

public class Floor {
    private final String floorId;
    private final List <Spot> spots;

    public Floor (String floorId, List <Spot> spots) {
        this.floorId = floorId;
        this.spots = spots;
    }

    public String getFloorId () { return floorId; }

    public List <Spot> getSpots () {
        return spots;
    }
}
