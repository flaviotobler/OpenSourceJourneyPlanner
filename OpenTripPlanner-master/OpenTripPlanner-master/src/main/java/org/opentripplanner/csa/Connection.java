package org.opentripplanner.csa;

import java.util.Calendar;

public class Connection implements Comparable { //Treeset braucht Comparable sonst exception
    private StopCSA departureStop;
    private StopCSA arrivalStop;
    private Calendar departureTime;
    private Calendar arrivalTime;
    private TripCSA trip;
    
    

    public Connection(StopCSA departureStop, StopCSA arrivalStop, Calendar startZeit,
            Calendar stopZeit, TripCSA trip) {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.departureTime = startZeit;
        this.arrivalTime = stopZeit;
        this.trip = trip;
    }

    public StopCSA getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(StopCSA departureStop) {
        this.departureStop = departureStop;
    }

    public StopCSA getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(StopCSA arrivalStop) {
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

    public TripCSA getTrip() {
        return trip;
    }

    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }

	@Override
	public int compareTo(Object arg0) {   //TreeSet sortieren  nach ...
		// TODO Auto-generated method stub
		
		/*
		return 0; dublikat wird nicht aufgenommen
		return 1; für in Reihenfolge ein begin mit erstem 1-2-3-4-5....
		return-1; Reihenfolge begin mit letztem 15-14-13-12...
		
		
		
		*/
		
		return 1;
	}
    
    
    
    

}
