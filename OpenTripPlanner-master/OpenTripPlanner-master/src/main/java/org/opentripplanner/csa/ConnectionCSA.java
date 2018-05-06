package org.opentripplanner.csa;


/**
 * This class implements the required connections for the Connection Scan Algorithm.It uses the Comparable Interface for the needed TreeSet.
 * TODO implement the comparable function
 * @author Christian
 *
 */
public class ConnectionCSA implements Comparable { 
    private StopCSA departureStop;
    private StopCSA arrivalStop;
    
    private int gtfsDepartureTime;
    private int gtfsArrivalTime;
    
    private int hDepartureTime;
    private int minDepartureTime;
    private int sDepartureTime;
    
    private int hArrivalTime;
    private int minArrivalTime;
    private int sArrivalTime;
    
    private TripCSA trip;
    
    
    /**
     * constructor for the connection.
     * @param departureStop
     * @param arrivalStop
     * @param gtfsDepartureTime
     * @param gtfsArrivalTime
     * @param trip
     */
    public ConnectionCSA(StopCSA departureStop, StopCSA arrivalStop, int gtfsDepartureTime,
    		int gtfsArrivalTime, TripCSA trip) {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        
        this.gtfsDepartureTime = gtfsDepartureTime;
        gtfsDepartureTimeConvertToHmS(this.gtfsDepartureTime);
        
        this.gtfsArrivalTime = gtfsArrivalTime;
        gtfsArrivalTimeConvertToHmS(this.gtfsArrivalTime);
   
        this.trip = trip;
    }

    /**
     * returns the departureStop
     * @return	departureStop as object
     */
    public StopCSA getDepartureStop() {
        return departureStop;
    }
    
    /**
     * sets the departureStop
     * @param departureStop as object
     */
    public void setDepartureStop(StopCSA departureStop) {
        this.departureStop = departureStop;
    }

    /**
     * returns the arrivalStop
     * @return	arrivalStop as object
     */
    public StopCSA getArrivalStop() {
        return arrivalStop;
    }

    /**
     * sets the arrivalStop
     * @param arrivalStop as object
     */
    public void setArrivalStop(StopCSA arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    /**
     * return the gtfsDepartureTime (hours,minutes,seconds) calculated in only seconds
     * @return gtfsDepartureTime as Integer in seconds
     */
	public int getGtfsDepartureTime() {
		return gtfsDepartureTime;
	}

	/**
	 * sets the gtfsDepartureTime only in seconds
	 * @param gtfsDepartureTime as Integer in seconds
	 */
	public void setGtfsDepartureTime(int gtfsDepartureTime) {
		this.gtfsDepartureTime = gtfsDepartureTime;
	}

	/**
	 * return the gtfsArrivalTime (hours,minutes,seconds) calculated in only seconds
	 * @return gtfsArrivalTime as Integer in seconds
	 */
	public int getGtfsArrivalTime() {
		return gtfsArrivalTime;
	}
	
	/**
	 * sets the gtfsArrivalTime only in seconds
	 * @param gtfsArrivalTime as Integer in seconds
	 */
	public void setGtfsArrivalTime(int gtfsArrivalTime) {
		this.gtfsArrivalTime = gtfsArrivalTime;
	}

	/**
	 * returns the hours of the DepartureTime
	 * @return hDepartureTime as Integer in hours
	 */
	public int gethDepartureTime() {
		return hDepartureTime;
	}

	/**
	 * sets the hours of the DepartureTime
	 * @param hDepartureTime as Integer in hours
	 */
	public void sethDepartureTime(int hDepartureTime) {
		this.hDepartureTime = hDepartureTime;
	}
	
	/**
	 * returns the seconds of the DepartureTime
	 * @return	sDepartureTime as Integer in seconds
	 */
	public int getsDepartureTime() {
		return sDepartureTime;
	}

	/**
	 * sets the seconds of the DepartureTime
	 * @param sDepartureTime as Integer in seconds
	 */
	public void setsDepartureTime(int sDepartureTime) {
		this.sDepartureTime = sDepartureTime;
	}

	/**
	 * returns the minutes of the DepartureTime
	 * @return minDepartureTime as Integer in minutes
	 */
	public int getMinDepartureTime() {
		return minDepartureTime;
	}

	/**
	 * sets the minutes of the DepartureTime
	 * @param minDepartureTime as Integer in minutes
	 */
	public void setMinDepartureTime(int minDepartureTime) {
		this.minDepartureTime = minDepartureTime;
	}

	/**
	 * returns the hours of the ArrivalTime
	 * @return hArrivalTime as Integer in hours
	 */
	public int gethArrivalTime() {
		return hArrivalTime;
	}
	
	/**
	 * sets the hours of the ArrivalTime
	 * @param hArrivalTime as Integer in hours
	 */
	public void sethArrivalTime(int hArrivalTime) {
		this.hArrivalTime = hArrivalTime;
	}

	/**
	 * returns the seconds of the ArrivalTime
	 * @return sArrivalTime as Integer in seconds
	 */
	public int getsArrivalTime() {
		return sArrivalTime;
	}

	/**
	 * sets the seconds of the ArrivalTime
	 * @param sArrivalTime as Integer in seconds
	 */
	public void setsArrivalTime(int sArrivalTime) {
		this.sArrivalTime = sArrivalTime;
	}

	/**
	 * returns the minutes of the ArrivalTime
	 * @return minArrivalTime as Integer in minutes
	 */
	public int getMinArrivalTime() {
		return minArrivalTime;
	}

	/**
	 * sets the minutes of the ArrivalTime
	 * @param minArrivalTime as Integer in minutes
	 */
	public void setMinArrivalTime(int minArrivalTime) {
		this.minArrivalTime = minArrivalTime;
	}
	
	/**
	 * return the trip of the connection
	 * @return trip as object
	 */
    public TripCSA getTrip() {
        return trip;
    }

    /**
     * sets the trip of the connection
     * @param trip as object
     */
    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }
    
    
    /**
     * The methode converts the seconds only format of the gtfs into a format of hours, minutes and seconds.
     * @param gtfsDepartureTime as Integer in seconds
     */
    public void gtfsDepartureTimeConvertToHmS(int gtfsDepartureTime){
        int hours = (int)(gtfsDepartureTime / (60 * 60) % 24);
        int minutes = (int)(gtfsDepartureTime / (60) % 60);
        int seconds = (int)(gtfsDepartureTime % 60);
       
        System.out.println("DepartureTime: " +hours+":"+ minutes+":"+seconds);
        
        this.hDepartureTime = hours;
        this.minDepartureTime = minutes;
        this.sDepartureTime = seconds;
    }
    /**
     * The methode converts the seconds only format of the gtfs into a format of hours, minutes and seconds.
     * @param gtfsArrivalTime as Integer in seconds
     */
    public void gtfsArrivalTimeConvertToHmS(int gtfsArrivalTime){
        int hours = (int)(gtfsArrivalTime / (60 * 60) % 24);
        int minutes = (int)(gtfsArrivalTime / (60) % 60);
        int seconds = (int)(gtfsArrivalTime % 60);

        System.out.println("ArrivalTime: " +hours+":"+ minutes+":"+seconds);
        
        this.hArrivalTime = hours;
        this.minArrivalTime = minutes;
        this.sArrivalTime = seconds;

    }
    

    /**
     * the methode should sort the TreeSets list when a connection is added
     * TODO
     *	@return 0 when it is a dublicate that is added, return 1 if it should be added after, return -1 if it should be added before
     */
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
