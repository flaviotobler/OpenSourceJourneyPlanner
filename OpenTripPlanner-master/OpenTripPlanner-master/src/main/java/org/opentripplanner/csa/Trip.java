package org.opentripplanner.csa;

public class Trip {
    
    private int TripId;
    private String TripName;
    
    public Trip(){}

    public Trip(int tripId, String tripName) {
        TripId = tripId;
        TripName = tripName;
    }

    public int getTripId() {
        return TripId;
    }

    public void setTripId(int tripId) {
        TripId = tripId;
    }

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    };
    
    

}
