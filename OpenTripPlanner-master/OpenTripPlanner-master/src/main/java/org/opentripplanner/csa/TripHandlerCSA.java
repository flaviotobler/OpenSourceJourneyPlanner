package org.opentripplanner.csa;

public class TripHandlerCSA {

    private TripCSA trip;
    private boolean tripFlag = false;
    private ConnectionCSA tripEnterConnection = null;
    
    public TripHandlerCSA(TripCSA trip){
        this.trip = trip;
    }

    public TripCSA getTrip() {
        return trip;
    }

    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }

    public boolean isTripFlag() {
        return tripFlag;
    }

    public void setTripFlag(boolean tripFlag) {
        this.tripFlag = tripFlag;
    }

    public ConnectionCSA getTripEnterConnection() {
        return tripEnterConnection;
    }

    public void setTripEnterConnection(ConnectionCSA tripEnterConnection) {
        this.tripEnterConnection = tripEnterConnection;
    }
}
