package org.opentripplanner.csa;

import java.util.Calendar;

public class Connection implements Comparable{
    private Stop departureStop;
    private Stop arrivalStop;
    private Calendar departureTime;
    private Calendar arrivalTime;
    private Trip trip;
    
    public Connection(){}

    public Connection(Stop departureStop, Stop arrivalStop, Calendar startZeit,
            Calendar stopZeit, Trip trip) {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.departureTime = startZeit;
        this.arrivalTime = stopZeit;
        this.trip = trip;
    }

    public Stop getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(Stop departureStop) {
        this.departureStop = departureStop;
    }

    public Stop getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(Stop arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    public Calendar getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Calendar departureTime) {
        this.departureTime = departureTime;
    }

    public Calendar getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Calendar arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
    
    

}
