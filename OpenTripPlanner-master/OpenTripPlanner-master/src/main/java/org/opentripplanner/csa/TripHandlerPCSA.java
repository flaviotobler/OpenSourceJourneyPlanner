package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TripHandlerPCSA {

    private TripCSA trip;
    private Calendar tripTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private ConnectionCSA tripExitConnection = null;
    
    public TripHandlerPCSA(TripCSA trip){
        this.trip = trip;
    }

    public TripCSA getTrip() {
        return trip;
    }

    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }

    public Calendar getTripTime(){
        return tripTime;
    }
    public void setTripTime(Calendar tripTime){
        this.tripTime = tripTime;
    }

    public ConnectionCSA getTripExitConnection() {
        return tripExitConnection;
    }

    public void setTripExitConnection(ConnectionCSA tripExitConnection) {
        this.tripExitConnection = tripExitConnection;
    }
}
