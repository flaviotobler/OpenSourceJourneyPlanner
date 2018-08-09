package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Trip-Handler class for the PCS. It provides changeable parameters so the TimeTable has not to be changed.
 * @author Flavio
 *
 */
public class TripHandlerPCSA {

    private TripCSA trip;
    private Calendar tripTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private ConnectionCSA tripExitConnection = null;
    /**
     * Constructor to create a new TripHandler-Object
     * @param trip
     */
    public TripHandlerPCSA(TripCSA trip){
        this.trip = trip;
    }
    /**
     * 
     * @return the Trip which belongs to the TripHandler
     */
    public TripCSA getTrip() {
        return trip;
    }
    /**
     * Sets the Trip which is referenced in the TripHandler
     * @param trip
     */
    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }
    /**
     * 
     * @return TripTime of the TripHandler
     */
    public Calendar getTripTime(){
        return tripTime;
    }
    /**
     * Sets the TripTime of the TripHandler
     * @param tripTime
     */
    public void setTripTime(Calendar tripTime){
        this.tripTime = tripTime;
    }
    /**
     * 
     * @return the ExitConnection of the TripHandler
     */
    public ConnectionCSA getTripExitConnection() {
        return tripExitConnection;
    }
    /**
     * Sets the ExitConnection of the TripHandler
     * @param tripExitConnection
     */
    public void setTripExitConnection(ConnectionCSA tripExitConnection) {
        this.tripExitConnection = tripExitConnection;
    }
}
