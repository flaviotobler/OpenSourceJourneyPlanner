package org.opentripplanner.csa;

/**
 * Trip-Handler class for the EAC. It provides changeable parameters so the TimeTable has not to be changed.
 * @author Flavio
 *
 */
public class TripHandlerCSA {

    private TripCSA trip;
    private boolean tripFlag = false;
    private ConnectionCSA tripEnterConnection = null;
    /**
     * Constructor to create a new TripHandler-Object
     * @param trip
     */
    public TripHandlerCSA(TripCSA trip){
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
     * @return a Boolean if the TripFlag is set.
     */
    public boolean isTripFlag() {
        return tripFlag;
    }

    /**
     * Sets the TripFlag to a specific value
     * @param tripFlag
     */
    public void setTripFlag(boolean tripFlag) {
        this.tripFlag = tripFlag;
    }

    /**
     * Returns the EnterConnection of the TripHandler
     * @return
     */
    public ConnectionCSA getTripEnterConnection() {
        return tripEnterConnection;
    }

    /**
     * Sets the EnterConnection for the TripHandler
     * @param tripEnterConnection
     */
    public void setTripEnterConnection(ConnectionCSA tripEnterConnection) {
        this.tripEnterConnection = tripEnterConnection;
    }
}
